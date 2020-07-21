package com.ll.job.domain;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "QRTZ_JOB_ENTITY")
public class JobEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "job_id")
    private String jobId;
    @Column(name = "job_name")
    private String jobName;          //job名称
    @Column(name = "job_group")
    private String jobGroup;         //job组名
    @Column(name = "jobType")
    private String jobType;        //下发类型
    @Column(name = "job_cron")
    private String jobCron;          //执行的cron
    @Column(name = "job_cnt")
    private String jobCnt;          //执行的期数
    @Column(name = "task_type")
    private String taskType;      //任务类别
    @Column(name = "job_description")
    private String description;   //job描述信息
    @Column(name = "vm_param")
    private String vmParam;       //vm参数

    private String fqdn;       //全限定名
    @Column(name = "jar_path")
    private String jarPath;       //job的jar路径,在这里我选择的是定时执行一些可执行的jar包
    private String status;        //job的执行状态,这里我设置为2,1,0且只有该值为1才会执行该Job,0:停止；1:执行；2:执行完毕
    private String creator;        //创建人
    private String modifier;        //更新人
    @Column(name = "create_date")
    private Date createDate;        //创建时间
    @Column(name = "modify_date")
    private Date modifyDate;        //更新时间
    public JobEntity() {
    }


    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public String getJobCnt() {
        return jobCnt;
    }

    public void setJobCnt(String jobCnt) {
        this.jobCnt = jobCnt;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVmParam() {
        return vmParam;
    }

    public void setVmParam(String vmParam) {
        this.vmParam = vmParam;
    }

    public String getFqdn() {
        return fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "JobEntity{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobType='" + jobType + '\'' +
                ", jobCron='" + jobCron + '\'' +
                ", jobCnt='" + jobCnt + '\'' +
                ", taskType='" + taskType + '\'' +
                ", description='" + description + '\'' +
                ", vmParam='" + vmParam + '\'' +
                ", fqdn='" + fqdn + '\'' +
                ", jarPath='" + jarPath + '\'' +
                ", status='" + status + '\'' +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }

    //新增Builder模式,可选,选择设置任意属性初始化对象
    public JobEntity(Builder builder) {
        jobId =  builder.id;
        jobName = builder.name;
        jobGroup = builder.group;
        jobCron = builder.cron;
        jobCnt = builder.cnt;
        jobType = builder.type;
        taskType = builder.taskType;
        description = builder.description;
        vmParam = builder.vmParam;
        vmParam = builder.fqdn;
        jarPath = builder.jarPath;
        status = builder.status;
        creator = builder.creator;
        modifier = builder.modifier;
        createDate = builder.createDate;
        modifyDate = builder.modifyDate;
    }
    public static class Builder {
        private String id;
        private String name = "";          //job名称
        private String group = "";         //job组名
        private String cron = "";          //执行的cron
        private String cnt = "";          //执行的期数
        private String type = "";          //下发类型
        private String taskType = "";     //job的参数
        private String description = "";   //job描述信息
        private String vmParam = "";       //vm参数
        private String fqdn = "";          //全限定名
        private String jarPath = "";       //job的jar路径
        private String status = "";        //job的执行状态,只有该值为OPEN才会执行该Job
        private String creator = "";        //创建人
        private String modifier = "";        //更新人
        private Date createDate = null;        //创建时间
        private Date modifyDate = null;        //更新时间
        public Builder withId(String i) {
            id = i;
            return this;
        }
        public Builder withName(String n) {
            name = n;
            return this;
        }
        public Builder withGroup(String g) {
            group = g;
            return this;
        }
        public Builder withCron(String c) {
            cron = c;
            return this;
        }
        public Builder withCnt(String c2) {
            cnt = c2;
            return this;
        }
        public Builder withT(String t) {
            type = t;
            return this;
        }
        public Builder withtaskType(String p) {
            taskType = p;
            return this;
        }
        public Builder withDescription(String d) {
            description = d;
            return this;
        }
        public Builder withVMtaskType(String vm) {
            vmParam = vm;
            return this;
        }
        public Builder withFQDN(String fq) {
            fqdn = fqdn;
            return this;
        }
        public Builder withJarPath(String jar) {
            jarPath = jar;
            return this;
        }
        public Builder withStatus(String s) {
            status = s;
            return this;
        }
        public Builder withCreator(String c) {
            creator = c;
            return this;
        }
       public Builder withModifier(String m) {
           modifier = m;
            return this;
        }
       public Builder withCreateDate(Date cd) {
           createDate = cd;
            return this;
        }
       public Builder withModifyDate(Date md) {
           modifyDate = md;
            return this;
        }
        public JobEntity newJobEntity() {
            return new JobEntity(this);
        }
    }
}