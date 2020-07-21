package com.ll.job.execute;


import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * :@DisallowConcurrentExecution : 此标记用在实现Job的类上面,意思是不允许并发执行.
 * :注意org.quartz.threadPool.threadCount线程池中线程的数量至少要多个,否则@DisallowConcurrentExecution不生效
 * :假如Job的设置时间间隔为3秒,但Job执行时间是5秒,设置@DisallowConcurrentExecution以后程序会等任务执行完毕以后再去执行,否则会在3秒时再启用新的线程执行
 * by CHENYB date 2019/07/31
 */
@Slf4j
@DisallowConcurrentExecution
@Component
public class Job2 implements Job {

    /**
     * Job2 核心方法,Quartz Job执行体.
     */
    @Override
    public void execute(JobExecutionContext executorContext){
        long startTime = System.currentTimeMillis();//执行时间
        //JobDetail中的JobDataMap是共用的,从getMergedJobDataMap获取的JobDataMap是全新的对象
        JobDataMap jobDataMap = executorContext.getMergedJobDataMap();
        log.info(jobDataMap.toString());
        long endTime = System.currentTimeMillis();
        log.info(">>>>>>> JOB2 ( ) >>>>>> 当前时间:{} ,Job2执行用时:{} ms\n",new Date( ),endTime - startTime);
    }

}