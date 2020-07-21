package com.ll.job.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 任务字典
 */
@Entity
@Table(name = "QRTZ_JOB_DICTIONARY")
public class JobDictionary implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String pathCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }
}
