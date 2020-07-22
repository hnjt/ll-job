package com.ll.job.service.impl;

import com.ll.commons.BaseService;
import com.ll.commons.CommonsUtil;
import com.ll.job.dao.JobDictionaryRepository;
import com.ll.job.dao.JobEntityRepository;
import com.ll.job.service.JobService;
import com.ll.utils.DateCommutativeCronUtil;
import com.ll.job.domain.JobDictionary;
import com.ll.job.domain.JobEntity;
import com.ll.job.mapper.JobMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * BeanJobService by CHENYB date 2019/7/31
 */
@Slf4j
@Service
@Transactional
public class JobServiceImpl extends BaseService implements JobService {

    @Autowired
    private JobEntityRepository repository;
    @Autowired
    private JobDictionaryRepository jobDictionaryRepository;
    @Autowired
    private JobMapper jobMapper;

    @Override
    public void destory() {
        this.jobMapper.qrtz_blob_triggersDestory();
        this.jobMapper.qrtz_calendarsDestory();
        this.jobMapper.qrtz_cron_triggersDestory();
        this.jobMapper.qrtz_fired_triggersDestory();
        this.jobMapper.qrtz_locksDestory();
        this.jobMapper.qrtz_paused_trigger_grpsDestory();
        this.jobMapper.qrtz_scheduler_stateDestory();
        this.jobMapper.qrtz_simple_triggersDestory();
        this.jobMapper.qrtz_simprop_triggersDestory();
        this.jobMapper.qrtz_triggersDestory();
        this.jobMapper.qrtz_job_detailsDestory();
    }

    //通过Id获取Job
    @Override
    public JobEntity getJobEntityById(String id) {
        return repository.getByJobId(id);
    }

    //从数据库中加载获取到所有Job
    @Override
    public List<JobEntity> loadJobs() {
        List<JobEntity> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    //Entity 转换 JobDataMap.(Job参数对象)
    @Override
    public JobDataMap getJobDataMap(JobEntity job) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put( "id",job.getJobId() );
        Map<String, Object> paramMap = this.getJobs( paramsMap );
        List<Map<String, Object>> jobs = (List<Map<String, Object>>) paramMap.get( "records" );
        if (jobs == null || jobs.size() == 0)
            throw new RuntimeException( "任务不存在" );
        JobDataMap map = new JobDataMap();
        map.putAll(jobs.get(0));
        return map;
    }
    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    @Override
    public JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap map ,Class<? extends Job> cls) {
        return JobBuilder.newJob(cls)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }
    //获取Trigger (Job的触发器,执行规则)
    @Override
    public Trigger getTrigger(JobEntity job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName(), job.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getJobCron()))
                .build();
    }
    //获取JobKey,包含Name和Group
    @Override
    public JobKey getJobKey(JobEntity job) {
        return JobKey.jobKey(job.getJobName(), job.getJobGroup());
    }

    //添加/修改定时任务
    @Override
    public JobEntity addOrUpdateJob(String userId,String code, JobEntity job) throws Exception {

        log.info( "manner of execution addOrUpdateJob!");

        if (null == job)
            throw new RuntimeException( "空数据" );

        String[] et = {"0","0","0","0"};
        String executionTime = job.getJobExecutionTime();
        if (StringUtils.isNotBlank( executionTime ))
            et=job.getJobExecutionTime().split( "," );

        //时间转换

        Long currentDate = Long.valueOf(et[0]);
        if (StringUtils.isNotBlank( executionTime )&&!et[0].equals( "0" )){
            job.setJobCron( DateCommutativeCronUtil.getCron( new Date(currentDate)).replace( "00","0" ) );
            job.setJobCnt( currentDate+"/1" );//指定时间执行任务，cnt为1
            et[0] = String.valueOf( currentDate );
            job.setJobExecutionTime( String.valueOf( et ) );
        }else if (StringUtils.isNotBlank( executionTime )&&et[0].equals( "0" )){
            job.setJobCron( this.getCronDate( et ) );

            if (StringUtils.isBlank( job.getJobCnt() )){
                et[4] = "N";
                job.setJobExecutionTime( String.valueOf( et ) );
                job.setJobCnt( "N/N" );
            }else
                job.setJobCnt("0/"+job.getJobCnt().toUpperCase());
        }
        //校验名字唯一性
        if (this.nameIsExist(job))
            throw new RuntimeException( "任务名称已存在" );
        job.setFqdn( this.getJobExecute( code));

        if (StringUtils.isBlank( job.getJobId() )){
            job.setJobId( CommonsUtil.getUUID() );
            job.setCreator( userId );
            job.setCreateDate( new Date(  ) );
            job = this.repository.save( job );
        }else {
            Map<String, String> params = new HashMap<>();
            params.put( "jobId" ,job.getJobId());
            Map<String, Object> paramMap = this.getJobs( params );
            List<Map<String, Object>> jobs = (List<Map<String, Object>>) paramMap.get( "records" );
            Map<String, Object> map = jobs.get( 0 );
            if (map.containsKey( "status" ) && Integer.valueOf( map.get( "status" ).toString() ) == 1)//运行中的任务不允许修改
                throw new RuntimeException( "运行中的任务不允许编辑" );
            if (StringUtils.isBlank( job.getJobName() ))
                job.setJobName( map.getOrDefault( "jobName","" ).toString() );
            if (StringUtils.isBlank( job.getJobGroup() ))
                job.setJobGroup( map.getOrDefault( "jobGroup","" ).toString() );
            if (StringUtils.isBlank( job.getJobExecutionTime() ))
                job.setJobExecutionTime( map.getOrDefault( "jobExecutionTime","" ).toString() );
            if (StringUtils.isBlank( job.getJobCron() ))
                job.setJobCron( map.getOrDefault( "jobCron","" ).toString() );
            if (StringUtils.isBlank( job.getJobCnt() ))
                job.setJobCnt( map.getOrDefault( "jobCnt","" ).toString() );
            if (StringUtils.isBlank( job.getJobDescription() ))
                job.setJobDescription( map.getOrDefault( "jobDescription","" ).toString() );
            if (StringUtils.isBlank( job.getStatus() ))
                job.setStatus( map.getOrDefault( "status","" ).toString() );
            if (StringUtils.isBlank( job.getCreator() ))
                job.setCreator( map.getOrDefault( "creator","" ).toString() );
            if (null == job.getCreateDate() ){
                Date createDate = (Date) map.get( "createDate" );
                job.setCreateDate(createDate);
            }

            job.setModifier( userId );
            job.setModifyDate( new Date(  ) );
            job = this.updateJob( job );
        }
        return job;
    }

    @Override
    public JobEntity updateJob(JobEntity jobEntity) {
        String cnt = jobEntity.getJobCnt() != null ? jobEntity.getJobCnt() : "";
        if (cnt.contains( "/" )&&!cnt.toUpperCase().contains( "N" )){
            String[] split = cnt.split( "/" );
            split[0] = String.valueOf( Integer.valueOf( split[0] ) + 1 );
            jobEntity.setJobCnt( split[0] + "/" + split[1] );
            if (split[0].equals( split[1] ))
                jobEntity.setStatus( "2" );
        }

        return this.entityManager.merge( jobEntity );
    }

    @Override
    public JobEntity updateJob(String id, String status) {
        JobEntity entity = this.getJobEntityById( id );
        entity.setStatus( status );
        return this.entityManager.merge( entity );
    }

    //cron 时间转换
    private String getCronDate(String[] et){

        String result = "";
        String weekDay = et[3];
        String monthDay = et[2];
        String yearDay = et[1];

        if (StringUtils.isNotBlank( weekDay )&&!weekDay.equals( "0" )){
            String[] strArray = {"SUN","MON","TUE","WED","THU","FRI","SAT"};
            result = "* * * ? * "+strArray[Integer.valueOf( weekDay )-1];//每周星期?执行
        }else if (StringUtils.isNotBlank( monthDay )&&!monthDay.equals( "0" )){
            result = "* * * "+monthDay+" * ?";//每月?号执行
        }else if (StringUtils.isNotBlank( yearDay )&&!yearDay.equals( "0" )){
            String[] split = yearDay.split( "-" );
            result = "* * * "+split[1]+" "+split[0]+" ? *";//每年的?月?日执行
        }
        return result;
    }

    //不包含自身,对象是否存在
    private Boolean nameIsExist(JobEntity job){
        JobEntity jobEntity = this.repository.getByJobName( job.getJobName() );
        if (null != jobEntity && !jobEntity.getJobId().equals( job.getJobId() ) )
            return true;
        else return false;
    }

    //删除定时任务
    @Override
    public void deleteJob(String id) {
        JobEntity entity = this.repository.getByJobId( id );
        if ("1".equals( entity.getStatus() ))
            throw new RuntimeException( "执行中的任务不允许删除" );
        this.repository.deleteById( id );
    }

    //获取Job执行体
    @Override
    public String getJobExecute(String code) {
        JobDictionary dictionary = this.jobDictionaryRepository.getByCode( code );
        return dictionary.getPathCode();
    }

    @Override
    public Map<String, Object> getJobs(Map<String, String> paramsMap) {
        int pageNum = Integer.valueOf(paramsMap.getOrDefault("pageNum", "0"));
        int pageSize = Integer.valueOf(paramsMap.getOrDefault("pageSize", "20"));
        int skip = pageNum < 1 ? 0 : pageSize * pageNum;

        List<Map<String,Object>> jobs = this.jobMapper.getJobs(paramsMap);
        List<Map<String, Object>> result = jobs.stream()
                .skip( skip )
                .limit( pageSize )
                .collect( Collectors.toList() );
        Map<String, Object> resultMap = new HashMap<>();
        int size = result.size();
        resultMap.put( "current", pageNum);
        resultMap.put( "pages", size == 0 ? 0 : (size / pageSize) + 1);
        resultMap.put( "records", result);
        resultMap.put( "searchCount", size>0?true:false);
        resultMap.put( "size", pageSize);
        resultMap.put( "total", size);
        return resultMap;
    }
}