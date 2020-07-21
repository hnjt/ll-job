package com.richfit.job.execute;

import com.richfit.job.domain.JobEntity;
import com.richfit.job.service.JobService;
import com.utils.HttpClientUtil;
import com.utils.ReflectUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * :@DisallowConcurrentExecution : 此标记用在实现Job的类上面,意思是不允许并发执行.
 * :注意org.quartz.threadPool.threadCount线程池中线程的数量至少要多个,否则@DisallowConcurrentExecution不生效
 * :假如Job的设置时间间隔为3秒,但Job执行时间是5秒,设置@DisallowConcurrentExecution以后程序会等任务执行完毕以后再去执行,否则会在3秒时再启用新的线程执行
 * by CHENYB date 2019/07/31
 */
@DisallowConcurrentExecution
@Component
public class DynamicJobLeak implements Job {
    private Logger logger = LoggerFactory.getLogger( DynamicJobLeak.class);

    @Value( "${iscp-api-merge.ip}" )
	private String leakIp;
	@Value( "${iscp-api-merge.port}" )
	private String leakPort;
    @Value( "${iscp-api-merge.method}" )
	private String leakMethod;

    @Autowired
    private JobService jobServiceImpl;

    /**
     * 系统漏扫(leak) 核心方法,Quartz Job执行体.
     */
    @Override
    public void execute(JobExecutionContext executorContext) throws JobExecutionException {
        long startTime = System.currentTimeMillis();//执行时间
        //JobDetail中的JobDataMap是共用的,从getMergedJobDataMap获取的JobDataMap是全新的对象
        JobDataMap jobDataMap = executorContext.getMergedJobDataMap();
        Map<String, String> data = new HashMap<>();

        data.putAll( this.getMap( jobDataMap ));
        String msg = HttpClientUtil.doRequest( "post", leakIp, leakPort, leakMethod, data );
        long endTime = System.currentTimeMillis();
        logger.info(">>>>>>> 系统漏扫 (com.richfit.job.execute.DynamicJobLeak ) >>>>>> 当前时间:{} ,Job执行用时:{} ms\n",new Date( ),endTime - startTime);
        logger.info("Job Create Msg : {}" ,msg);
        JobEntity jobEntity = new JobEntity();
        ReflectUtils.bindingPropertyValue( data,jobEntity);
        JobEntity job = this.jobServiceImpl.updateJob( jobEntity );
        logger.info("Job Update Msg : {}" ,job);
    }

    private Map<String, String> getMap (JobDataMap jobDataMap){
        Map<String, String> map = new HashMap<>(  );
        map.putAll( ReflectUtils.mapObjToHashMapStr( jobDataMap ));
        map.put( "isSchedule","1" );//定时任务创建传1
        map.put( "userId",map.get( "creator" ) );
        map.put( "name",map.get( "jobName" ) );
        map.put( "id",map.get( "jobId" ) );
        return map;
    }
}