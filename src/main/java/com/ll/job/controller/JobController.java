package com.ll.job.controller;

import com.ll.commons.BaseController;
import com.ll.commons.ResultVo;
import com.ll.utils.IMapUtil;
import com.ll.job.domain.JobEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 此时如果在数据库中手动修改某个Job的执行cron，并不会马上生效,重启定时任务才会生效
 * 定时任务接口 by CHENYB date 2019-07-31
 */
@Slf4j
@Api(tags = "定时任务")
@RestController
@RequestMapping("/job")
public class JobController extends BaseController {

    @ApiOperation(value = "根据ID重启Job", notes = "根据ID重启定时任务")
    @PostMapping(value = "/refreshJobById")
    public String refreshJobById(
            HttpServletRequest request,
            @ApiParam(required = true, name = "id", value = "id") @RequestParam(name = "id", required = true) String id,
            @ApiParam(required = true, name = "taskType", value = "任务") @RequestParam(name = "taskType", required = true) String taskType
    )throws Exception{

        ResultVo resultVo = new ResultVo();
        this.jobService.updateJob( id,"1" );
        resultVo.setSuccess( super.refresh( id ) );
        return resultVo.toJSONString();
    }


    @ApiOperation(value = "重启数据库中所有的Job", notes = "重启数据库中所有的定时任务")
    @GetMapping(value = "/refreshAllJob")
    public String refreshAllJob(
            HttpServletRequest request
    ){
        ResultVo resultVo = new ResultVo();
        super.refreshAll();
        resultVo.setSuccess( true );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "根据id停止任务", notes = "根据id停止任务")
    @PostMapping(value = "/stopJobById")
    public String stopJobById(
            HttpServletRequest request,
            @ApiParam(required = true, name = "id", value = "id") @RequestParam(name = "id", required = true) String id
    )throws Exception {
        ResultVo resultVo = new ResultVo();
        super.stopJob( id );
        this.jobService.updateJob( id,"0" );
        resultVo.setSuccess( true );
        resultVo.setMsg( "成功" );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "停止所有定时任务", notes = "停止所有定时任务")
    @PostMapping(value = "/stopJobs")
    public String stopJobs(
    ){
        ResultVo resultVo = new ResultVo();
        try {
            super.stopAllJobs();
            resultVo.setSuccess( true );
            resultVo.setMsg( "成功" );
        }catch (Exception e){
            resultVo.setSuccess( false );
            e.printStackTrace();
        }
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "添加/修改定时任务", notes = "添加/修改定时任务[正在运行的任务不允许修改]")
    @PostMapping(value = "/addOrUpdateJob")
    public String addOrUpdateJob(
            HttpServletRequest request,
            @ApiParam(required = false, name = "userId", value = "创建人(创建时)/更新人(修改时)") @RequestParam(name = "userId", required = false) String userId,
            @ApiParam(required = false, name = "jobId", value = "id修改时传入") @RequestParam(name = "jobId", required = false) String jobId,
            @ApiParam(required = true, name = "jobName", value = "定时任务名称") @RequestParam(name = "jobName", required = true) String jobName,
            @ApiParam(required = true, name = "jobGroup", value = "定时任务组名") @RequestParam(name = "jobGroup", required = true) String jobGroup,
            @ApiParam(required = false, name = "jobDescription", value = "定时任务描述") @RequestParam(name = "jobDescription", required = false) String jobDescription,
            @ApiParam(required = false, name = "status", value = "状态(1:开启,0关闭)，默认开启状态") @RequestParam(name = "status", required = false) String status,
            @ApiParam(required = false, name = "jobCnt", value = "周期任务执行期数，N：不限制次数") @RequestParam(name = "jobCnt", required = false) String jobCnt,
            @ApiParam(required = false, name = "jobExecutionTime",
                    value = "执行时间 (0,0,0,0)\n" +
                            "     第1位指定时间戳（标识指定时间）；\n" +
                            "     第2、3、4位表示执行周期的年、月、周\n" +
                            "     年内时间(1~12月-1~31日)\n" +
                            "     月内时间(1~31日)\n" +
                            "     周内时间(1~7星期)")
            @RequestParam(name = "jobExecutionTime", required = false) String jobExecutionTime,
            @ApiParam(required = true, name = "code", value = "选定任务执行类型，在任务字典中获取") @RequestParam(name = "code", required = true) String code

    ) throws Exception {

        if (StringUtils.isNotBlank( jobCnt )){
            Integer cntInt = -1;
            if (!jobCnt.toUpperCase().equals( "N" )){
                cntInt = Integer.valueOf( jobCnt );
            }
            if ((cntInt > 60 || cntInt < 0) && !"N".equals( jobCnt.toUpperCase() ))// cnt 合法范围值
                throw new RuntimeException( "运行次数超出预设范围");
        }

        Map<String, String> paramsMap = super.initParams( request );

        JobEntity jobEntity = IMapUtil.mapToBean( JobEntity.class, paramsMap);
        ResultVo resultVo = new ResultVo();
        jobEntity = this.jobService.addOrUpdateJob( userId,code,jobEntity);
        if (jobEntity.getStatus().equals( "1" ))
            this.refresh( jobEntity.getJobId());//重启指定任务
        resultVo.setSuccess( true );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "删除定时任务持久化", notes = "删除定时任务持久化")
    @PostMapping(value = "/deleteJobs")
    public String deleteJobs (
            HttpServletRequest request,
            @ApiParam(required = true, name = "ids", value = "多个id逗号拼接") @RequestParam(name = "ids", required = true) String ids,
            @ApiParam(required = true, name = "userId", value = "userId") @RequestParam(name = "userId", required = true) String userId
    )throws Exception {
        ResultVo resultVo = new ResultVo();
        String[] split = ids.split( "," );
        for (int i = 0; i < split.length; i++) {
            super.stopJob( split[i] );//清除Job注册队列
            this.jobService.deleteJob( split[i] );
        }
        resultVo.setSuccess( true );
        resultVo.setMsg( "成功" );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "获取定时任务", notes = "获取定时任务")
    @GetMapping(value = "/getPageForJobs")
    public String getJobs (
            HttpServletRequest request,
            @ApiParam(required = true, name = "pageNum", value = "分页第几页") @RequestParam(name = "pageNum", required = true) Integer pageNum,
            @ApiParam(required = true, name = "pageSize", value = "每页数据数量") @RequestParam(name = "pageSize", required = true) Integer pageSize,
            @ApiParam(required = false, name = "name", value = "任务名") @RequestParam(name = "name", required = false) String name,
            @ApiParam(required = false, name = "ip", value = "包含IP") @RequestParam(name = "ip", required = false) String ip,
            @ApiParam(required = true, name = "userId", value = "用户Id") @RequestParam(name = "userId", required = true) String userId
    ){

        Map<String, String> paramsMap = super.initParams( request );
        ResultVo resultVo = new ResultVo();
        Map<String,Object> pagesMap = this.jobService.getJobs(paramsMap);
        resultVo.setSuccess( true );
        resultVo.setMsg( "成功" );
        resultVo.setData( pagesMap );
        return resultVo.toJSONString();
    }


    @ApiOperation(value = "任务详情", notes = "[ 通过ID获取任务详情 ]")
    @GetMapping(value = "/getJob")
    public String getJob(
            HttpServletRequest request,
            @ApiParam(required = true, name = "id", value = "任务ID") @RequestParam(name = "id", required = true) String id
    ){

        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.jobService.getJobEntityById( id ) );
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "任务字典", notes = "[ 获取任务权限定名信息 ]")
    @GetMapping(value = "/findAllDictionary")
    public String findAllDictionary(
            HttpServletRequest request
    ){
        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.jobService.findAllDictionary(  ));
        return resultVo.toJSONString();
    }
}
