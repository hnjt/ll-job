package com.ll.job.service;

import com.ll.job.domain.JobEntity;
import org.quartz.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Job 接口 by CHENYB date 2019-07-31
 */
public interface JobService {

    //通过Id获取Job
    JobEntity getJobEntityById(String id);

    //从数据库中加载获取到所有Job
    List<JobEntity> loadJobs() ;

    //获取JobDataMap.(Job参数对象)
    JobDataMap getJobDataMap(JobEntity job) ;

    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap map ,Class<? extends Job> cls);

    //获取Trigger (Job的触发器,执行规则)
    Trigger getTrigger(JobEntity job) ;

    //获取JobKey,包含Name和Group
    JobKey getJobKey(JobEntity job) ;

    //添加/修改定时任务
    JobEntity addOrUpdateJob (Map<String, String> paramsMap, JobEntity job) throws ParseException;

    //删除Job持久化
    void deleteJob (String id);

    //获取执行体对象
    String getJobExecute(String taskType);

    //获取job详情集合
    Map<String, Object> getJobs(Map<String, String> paramsMap);


    JobEntity updateJob(JobEntity jobEntity);

    /**
     * 只做任务状态控制
     * @param id
     * @param status
     * @return
     */
    JobEntity updateJob(String id, String status);

    void destory();
}
