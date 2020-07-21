package com.ll.job.controller;

import com.commons.BaseController;
import com.ll.job.domain.JobEntity;
import com.utils.ReflectUtils;
import com.utils.ResultVo;
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
@Api(description = "定时任务接口")
@RestController
@RequestMapping("/job")
public class JobController extends BaseController {

    @ApiOperation(value = "根据ID重启Job", notes = "根据ID重启定时任务")
    @PostMapping(value = "/refreshJobById")
    public String refreshJobById(
            HttpServletRequest request,
            @ApiParam(required = true, name = "id", value = "id") @RequestParam(name = "id", required = true) String id,
            @ApiParam(required = true, name = "taskType", value = "任务") @RequestParam(name = "taskType", required = true) String taskType
    ){

        ResultVo resultVo = new ResultVo();
        try {
            this.jobService.updateJob( id,"1" );
            resultVo.setSuccess( super.refresh( id ) );
        }catch (Exception e){
            resultVo.setSuccess( false );
            e.printStackTrace();
        }
        return resultVo.toJSONString();
    }


    @ApiOperation(value = "重启数据库中所有的Job", notes = "重启数据库中所有的定时任务")
    @GetMapping(value = "/refreshAllJob")
    public String refreshAllJob(
            HttpServletRequest request
    ){
        ResultVo resultVo = new ResultVo();
        try {
            super.refreshAll();
            resultVo.setSuccess( true );
        }catch (Exception e){
            resultVo.setSuccess( false );
            e.printStackTrace();
        }
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "根据id停止任务", notes = "根据id停止任务")
    @PostMapping(value = "/stopJobById")
    public String stopJobById(
            HttpServletRequest request,
            @ApiParam(required = true, name = "id", value = "id") @RequestParam(name = "id", required = true) String id
    ){
        ResultVo resultVo = new ResultVo();
        try {
            super.stopJob( id );
            this.jobService.updateJob( id,"0" );
            resultVo.setSuccess( true );
            resultVo.setMsg( "成功" );
        }catch (Exception e){
            e.printStackTrace();
        }
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
            @ApiParam(required = false, name = "id", value = "id修改时传入") @RequestParam(name = "id", required = false) String id,
            @ApiParam(required = false, name = "createType", value = "创建类别") @RequestParam(name = "createType", required = false) String createType,
            @ApiParam(required = false, name = "ruleType", value = "规则类型") @RequestParam(name = "ruleType", required = false) String ruleType,
            @ApiParam(required = false, name = "toolType", value = "工具类型 0：自动选择工具 1：指定工具") @RequestParam(name = "toolType", required = false) String toolType,
            @ApiParam(required = false, name = "tools", value = "手动指定工具，当且仅当toolType的值为1时。该字段才有意义。可选值有:NF_RSAS_V6,NF_SV_V5,VENUS_SV_V6.多个之间用逗号分隔") @RequestParam(name = "tools", required = false) String tools,
            @ApiParam(required = false, name = "taskOrgId", value = "任务组织Id") @RequestParam(name = "taskOrgId", required = false) String taskOrgId,
            @ApiParam(required = false, name = "taskOrgName", value = "任务组织名称") @RequestParam(name = "taskOrgName", required = false) String taskOrgName,
            @ApiParam(required = true, name = "checkType", value = "检查类型") @RequestParam(name = "checkType", required = true) String checkType,
            @ApiParam(required = true, name = "ip", value = "IP地址段;多个IP地址之间用分号(;)分隔") @RequestParam(name = "ip", required = true) String ip,
            @ApiParam(required = false, name = "executeType", value = "资产探测扫描类型") @RequestParam(name = "executeType", required = false) String executeType,
            @ApiParam(required = false, name = "ports", value = "端口号") @RequestParam(name = "ports", required = false) String ports,
            @ApiParam(required = false, name = "snmp", value = "团体名") @RequestParam(name = "snmp", required = false) String snmp,
            @ApiParam(required = false, name = "ipLists", value = "前端将IP段分组后返回的分解子任务的标准") @RequestParam(name = "ipLists", required = false) String ipLists,
            @ApiParam(required = true, name = "token", value = "用户信息token") @RequestParam(name = "token", required = true) String token,
            @ApiParam(required = false, name = "userId", value = "创建人(创建时)/更新人(修改时)") @RequestParam(name = "userId", required = false) String userId,
            @ApiParam(required = true, name = "name", value = "定时任务名称") @RequestParam(name = "name", required = true) String name,
            @ApiParam(required = true, name = "group", value = "定时任务组名") @RequestParam(name = "group", required = true) String group,
            @ApiParam(required = false, name = "currentDate", value = "执行时间(指定时间戳)") @RequestParam(name = "currentDate", required = false) String currentDate,
            @ApiParam(required = true, name = "taskType", value = "任务类型（资产探测为asset_detect，系统漏扫为leak）") @RequestParam(name = "taskType", required = true) String taskType,
            @ApiParam(required = false, name = "description", value = "定时任务描述") @RequestParam(name = "description", required = false) String description,
            @ApiParam(required = false, name = "vmParam", value = "定时任务数据") @RequestParam(name = "vmParam", required = false) String vmParam,
            @ApiParam(required = false, name = "jarPath", value = "jar路径") @RequestParam(name = "jarPath", required = false) String jarPath,
            @ApiParam(required = true, name = "status", value = "状态(1:开启,0关闭)") @RequestParam(name = "status", required = true) String status,
            @ApiParam(required = false, name = "scheduleType", value = "时间类型(1指定日期/2指定周期)") @RequestParam(name = "scheduleType", required = false) String scheduleType,
            @ApiParam(required = false, name = "weekDay", value = "周内时间(1~7星期)") @RequestParam(name = "weekDay", required = false) String weekDay,
            @ApiParam(required = false, name = "monthDay", value = "月内时间(1~31日)") @RequestParam(name = "monthDay", required = false) String monthDay,
            @ApiParam(required = false, name = "yearDay", value = "年内时间(1~12月-1~31日)") @RequestParam(name = "yearDay", required = false) String yearDay,
            @ApiParam(required = false, name = "cnt", value = "周期任务执行期数，N：不限制次数") @RequestParam(name = "cnt", required = false) String cnt
    ){

        if (StringUtils.isNotBlank( cnt )){
            try {
                Integer cntInt = -1;
                if (!cnt.toUpperCase().equals( "N" )){
                    cntInt = Integer.valueOf( cnt );
                }
                if (cntInt > 60 || cntInt < 0)// cnt 合法范围值
                    throw new RuntimeException( "运行次数超出预设范围");
            }catch (Exception e){
                throw new RuntimeException( "cnt 参数必须为int类型" );
            }
        }

        Map<String, String> paramsMap = super.initParams( request );
        JobEntity jobEntity = new JobEntity();
        ReflectUtils.bindingPropertyValue( paramsMap,jobEntity);
        ResultVo resultVo = new ResultVo();
        try {
            jobEntity = this.jobService.addOrUpdateJob( paramsMap,jobEntity);
            if (jobEntity.getStatus().equals( "1" ))
                this.refresh( jobEntity.getJobId());//重启指定任务
            resultVo.setSuccess( true );
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultVo.toJSONString();
    }

    @ApiOperation(value = "删除定时任务持久化", notes = "删除定时任务持久化")
    @PostMapping(value = "/deleteJobs")
    public String deleteJobs (
            HttpServletRequest request,
            @ApiParam(required = true, name = "ids", value = "多个id逗号拼接") @RequestParam(name = "ids", required = true) String ids,
            @ApiParam(required = true, name = "token", value = "token") @RequestParam(name = "token", required = true) String token,
            @ApiParam(required = true, name = "userId", value = "userId") @RequestParam(name = "userId", required = true) String userId
    ){
        ResultVo resultVo = new ResultVo();
        try {
            String[] split = ids.split( "," );
            for (int i = 0; i < split.length; i++) {
                super.stopJob( split[i] );//清除Job注册队列
                this.jobService.deleteJob( split[i] );
            }
            resultVo.setSuccess( true );
            resultVo.setMsg( "成功" );
        }catch (Exception e){
            e.printStackTrace();
        }
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
            @ApiParam(required = true, name = "token", value = "用户信息token") @RequestParam(name = "token", required = true) String token,
            @ApiParam(required = true, name = "userId", value = "用户Id") @RequestParam(name = "userId", required = true) String userId
    ){

        Map<String, String> paramsMap = super.initParams( request );
        ResultVo resultVo = new ResultVo();
        try {
            Map<String,Object> pagesMap = this.jobService.getJobs(paramsMap);
            resultVo.setSuccess( true );
            resultVo.setMsg( "成功" );
            resultVo.setData( pagesMap );
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultVo.toJSONString();
    }


    @ApiOperation(value = "任务详情", notes = "[ 通过ID获取任务详情 ]")
    @GetMapping(value = "/getJob")
    public String getJob(
            HttpServletRequest request,
            @ApiParam(required = true, name = "token", value = "用户信息token") @RequestParam(name = "token", required = true) String token,
            @ApiParam(required = true, name = "id", value = "任务ID") @RequestParam(name = "id", required = true) String id
    ){

        ResultVo resultVo = new ResultVo();
        resultVo.setData( this.jobService.getJobEntityById( id ) );
        return resultVo.toJSONString();
    }
}
