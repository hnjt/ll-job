/**
 * 鏂囦欢鍚嶏細BaseController.java
 * 
 *鍖椾含涓补鐟為淇℃伅鎶�鏈湁闄愯矗浠诲叕鍙�(http://www.richfit.com)
 * Copyright 漏 2017 Richfit Information Technology Co., LTD. All Right Reserved.
 */
package com.base;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import com.exception.ExceptionEnum;
import com.exception.MyException;
import com.richfit.job.domain.JobEntity;
import com.richfit.job.service.JobService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller 基类 by CHENYB data 2019-07-31
 */
@RestController
public class BaseController {

	public static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    protected JobService jobService;

    //初始化启动所有的Job
    private @PostConstruct void initialize() {

        try {
            for (JobEntity job : jobService.loadJobs()) {

                this.refresh( job.getJobId() );
            }
            logger.info("INIT JOB SUCCESS");
        } catch (Exception e) {
            logger.info("INIT JOB EXCEPTION : " + e.getMessage());
            e.printStackTrace();
        }
    }

    //销毁偏移量持久化
    @PreDestroy
    public void destory() {
        try {
            this.jobService.destory();
            logger.info("DESTORY JOB SUCCESS");
        } catch (Exception e) {
            logger.info("DESTORY JOB EXCEPTION : " + e.getMessage());
            e.printStackTrace();
        }
    }

    //根据ID重启某个Job
    public Boolean refresh(String id ) throws SchedulerException {
        boolean result = false;
        Map<String, Object> paramsMap = this.stopJob( id );
        JobEntity entity;
        if (paramsMap.containsKey("entity")&&paramsMap.get("entity") == null) return false;
        synchronized (logger) {
            Scheduler scheduler;
            try {
                scheduler = schedulerFactoryBean.getScheduler();
                entity = (JobEntity) paramsMap.get("entity");
                JobKey jobKey = (JobKey) paramsMap.get( "jobKey" );
                JobDataMap map = jobService.getJobDataMap(entity);
                Class<? extends Job> aClass = (Class<? extends Job>) Class.forName( entity.getFqdn() );
                JobDetail jobDetail = jobService.getJobDetail(jobKey, entity.getDescription(), map , aClass );
                if (StringUtils.isNotBlank( entity.getStatus() )
                        && StringUtils.isNotBlank(entity.getJobCnt())
                        && entity.getStatus().equals("1")
                ) {//判定设定的启动状态是否允许,执行周期是否在0-60之间或者为N，否则不允许启动
                    String cnt = entity.getJobCnt();
                    String[] split = cnt.split( "/" );
                    if (
                            cnt.toUpperCase().equals( "N/N" )||
                                    (
                                            Integer.valueOf( split[1] )>0&&Integer.valueOf( split[1] )<=60//执行次数0-60之间
                                            && split[0] != split[1]//不能是执行结束的任务
                                    )
                    ){
                        if (StringUtils.isBlank( entity.getJobCron() ))
                            throw new RuntimeException( "cron 参数不能为空" );
                        scheduler.scheduleJob(jobDetail, jobService.getTrigger(entity));
                        result = true;
                    }

                } else {
                    result = false;
                }
            }catch (NumberFormatException nfe){
                result = false;
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (SchedulerException se){
                logger.info( "任务队列中存在不可能执行的任务，id：{}",id);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 重新启动所有的job
     */
    public Boolean refreshAll() {
        boolean result = false;
        try {
            initialize();
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    //(*Attention)This is the only Job you can use
    private void reStartAllJobs(Class<? extends Job> cls) throws SchedulerException {
        synchronized (logger) {                                                       //只允许一个线程进入操作
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if (this.stopAllJobs()){
                for (JobEntity job : jobService.loadJobs()) {                               //从数据库中注册的所有JOB
                    logger.info("Job register name : {} , group : {} , cron : {}", job.getJobName(), job.getJobGroup(), job.getJobCron());
                    JobDataMap map = jobService.getJobDataMap(job);
                    JobKey jobKey = jobService.getJobKey(job);
                    JobDetail jobDetail = jobService.getJobDetail(jobKey, job.getDescription(), map , cls);
                    if (job.getStatus().equals("1")) scheduler.scheduleJob(jobDetail, jobService.getTrigger(job));
                    else
                        logger.info("Job jump name : {} , Because {} status is {}", job.getJobName(), job.getJobName(), job.getStatus());
                }
            }
        }
    }

    //停止一个Job
    public Map<String, Object> stopJob( String id )throws SchedulerException {
        JobEntity entity = jobService.getJobEntityById(id);
        HashMap<String, Object> resultMap = new HashMap<>();
        try {
            if (entity == null)
                throw new MyException( ExceptionEnum.EXCEPTION_DATA_NULL );
            synchronized (logger) {
                JobKey jobKey = jobService.getJobKey( entity );
                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                scheduler.pauseJob( jobKey );
                scheduler.unscheduleJob( TriggerKey.triggerKey( jobKey.getName(), jobKey.getGroup() ) );
                scheduler.deleteJob( jobKey );
                resultMap.put( "jobKey",jobKey );
                resultMap.put( "entity",entity );
            }
        }catch ( SchedulerException e){
            e.printStackTrace();
        }
        return resultMap;
    }

    //暂停所有Job
    public Boolean stopAllJobs (){
        boolean result = false;
        try {
            synchronized (logger) {                                                         //只允许一个线程进入操作
                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                Set<JobKey> set = scheduler.getJobKeys( GroupMatcher.anyGroup() );
                scheduler.pauseJobs( GroupMatcher.anyGroup() );                               //暂停所有JOB
                for (JobKey jobKey : set) {                                                 //删除从数据库中注册的所有JOB
                    scheduler.unscheduleJob( TriggerKey.triggerKey( jobKey.getName(), jobKey.getGroup() ) );
                    scheduler.deleteJob( jobKey );
                }
            }
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将 HttpServletRequest 获得的参数封装成Map
     */
    protected Map<String, String> initRequestParams(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        if (request == null) {
            return paramMap;
        }
        Enumeration<?> paramNames = request.getParameterNames();
        if (request != null && paramNames != null && paramNames.hasMoreElements()) {
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                if (paramValues.length == 1) {
                    paramMap.put(paramName, paramValues[0]);
                } else {
                    paramMap.put(paramName, ArrayUtils.toString(paramValues));
                }
            }
        }
        return paramMap;
    }

}
