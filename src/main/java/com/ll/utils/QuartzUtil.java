package com.ll.utils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 定时任务工具类 by CHENYB date 2019-07-30
 */
@Slf4j
@Component
public class QuartzUtil {

    /**
     * 注入调度器
     */
    @Autowired
    private Scheduler scheduler;
    /**
     * 默认Job组名
     */
    private static String JOB_GROUP_NAME = "DEFAULT_JOB_GROUP_NAME";
    /**
     * 默认触发器组名
     */
    private static String TRIGGER_GROUP_NAME = "DEFAULT_TRIGGER_GROUP_NAME";


    public boolean addCronJob(String jobName, String jobGroup, String triggerName, String triggerGroup, String cronExpression, Map<String,Object> extraParam, Class<? extends Job> JobClass){

        try {
            //使用JobDetail包装job
            JobDetail jobDetail = JobBuilder
                    .newJob(JobClass)
                    .withIdentity(jobName, jobGroup)
                    .build();
            if (extraParam != null) {
                //封装信息内容
                jobDetail.getJobDataMap().putAll(extraParam);
            }
            //设置引发器
            CronTrigger cronTrigger = TriggerBuilder
                    .newTrigger()
                    .startNow()
                    .withIdentity(triggerName,triggerGroup)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .forJob( jobName,jobGroup )
                    .build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}