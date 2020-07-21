package com.richfit.job.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "QRTZ_JOB_DTO")
public class JobDTO implements Serializable {

    private static final long serialVersionUID = 4122384962907036643L;

    @Id
    @Column(name = "job_id")
    private String jobId;
    @Column(name = "create_type")
    private String createType;//创建类别
    @Column(name = "rule_type")
    private String ruleType;//规则类型
    @Column(name = "tool_type")
    private String toolType;//工具类型 0：自动选择工具 1：指定工具
    //手动指定工具，当且仅当toolType的值为1时。该字段才有意义。可选值有:NF_RSAS_V6,NF_SV_V5,VENUS_SV_V6.多个之间用逗号分隔
    private String tools;
    @Column(name = "task_org_id")
    private String taskOrgId;//taskOrgId任务组织Id
    @Column(name = "task_org_name")
    private String taskOrgName;//taskOrgName任务组织名称
    @Column(name = "check_type")
    private String checkType;//检查类型
    private String ip;//IP地址段;多个IP地址之间用分号(;)分隔
    @Column(name = "execute_type")
    private String executeType;//资产探测扫描类型
    private String ports;//端口号
    private String snmp;//前端将IP段分组后返回的分解子任务的标准
    @Column(name = "ip_lists")
    private String ipLists;//团体名
    @Column(name = "current_date")
    private String currentDate;
    @Column(name = "week_day")
    private String weekDay;
    @Column(name = "month_day")
    private String monthDay;
    @Column(name = "year_day")
    private String yearDay;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getTaskOrgId() {
        return taskOrgId;
    }

    public void setTaskOrgId(String taskOrgId) {
        this.taskOrgId = taskOrgId;
    }

    public String getTaskOrgName() {
        return taskOrgName;
    }

    public void setTaskOrgName(String taskOrgName) {
        this.taskOrgName = taskOrgName;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getSnmp() {
        return snmp;
    }

    public void setSnmp(String snmp) {
        this.snmp = snmp;
    }

    public String getIpLists() {
        return ipLists;
    }

    public void setIpLists(String ipLists) {
        this.ipLists = ipLists;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(String monthDay) {
        this.monthDay = monthDay;
    }

    public String getYearDay() {
        return yearDay;
    }

    public void setYearDay(String yearDay) {
        this.yearDay = yearDay;
    }
}