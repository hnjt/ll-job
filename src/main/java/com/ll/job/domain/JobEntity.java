package com.ll.job.domain;
import com.ll.commons.EntityVo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "QRTZ_JOB_ENTITY")
public class JobEntity extends EntityVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    private String jobId;

    private String jobName;          //job名称

    /**
     * 执行时间 (0,0,0,0,0)
     * 第1位指定时间戳（标识指定时间）；
     * 第2、3、4位表示执行周期的年、月、周
     * 第5位表示 确切的时间 24小时制 例如：23:22:11
     * 第5位表示 轮询时间 例如
     * **:**:01 间隔1秒钟执行
     * **:01:** 间隔1分钟执行
     * 01:**:** 间隔1小时执行
     */
    private String jobExecutionTime;

    private String jobGroup;         //job组名

    private String jobCron;          //执行的cron

    private String jobDescription;   //job描述信息

    private String fqdn;       //全限定名

    //job的执行状态,这里我设置为2,1,0且只有该值为1才会执行该Job,0:停止；1:执行；2:执行完毕
    private String status;

    private String jobCnt;          //执行的期数



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

    public String getJobExecutionTime() {
        return jobExecutionTime;
    }

    public void setJobExecutionTime(String jobExecutionTime) {
        this.jobExecutionTime = jobExecutionTime;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getFqdn() {
        return fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobCnt() {
        return jobCnt;
    }

    public void setJobCnt(String jobCnt) {
        this.jobCnt = jobCnt;
    }

    //新增Builder模式,可选,选择设置任意属性初始化对象
    public JobEntity(Builder builder) {
        jobId =  builder.id;
        jobName = builder.name;
        jobExecutionTime = builder.executionTime;
        jobGroup = builder.group;
        jobCron = builder.cron;
        jobDescription = builder.description;
        fqdn = builder.fqdn;
        status = builder.status;
        jobCnt = builder.cnt;
        creator = builder.creator;
        modifier = builder.modifier;
        createDate = builder.createDate;
        modifyDate = builder.modifyDate;
    }
    public static class Builder {
        private String id;
        private String name = "";          //job名称
        private String executionTime = "";          //下发类型
        private String group = "";         //job组名
        private String cron = "";          //执行的cron
        private String description = "";   //job描述信息
        private String fqdn = "";          //全限定名
        private String status = "";        //job的执行状态,只有该值为OPEN才会执行该Job
        private String cnt = "";          //执行的期数
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
        public Builder withET(String et) {
            executionTime = et;
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
        public Builder withDescription(String d) {
            description = d;
            return this;
        }
        public Builder withFQDN(String fq) {
            fqdn = fqdn;
            return this;
        }
        public Builder withStatus(String s) {
            status = s;
            return this;
        }
        public Builder withCnt(String c2) {
            cnt = c2;
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