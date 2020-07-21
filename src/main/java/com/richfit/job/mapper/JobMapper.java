package com.richfit.job.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface JobMapper {


    List<Map<String, Object>> getJobs(@Param( "param" ) Map<String,String> paramsMap);

    /*-----------销毁偏移量持久化 begin-----------*/
    void qrtz_blob_triggersDestory();
    void qrtz_calendarsDestory();
    void qrtz_cron_triggersDestory();
    void qrtz_fired_triggersDestory();
    void qrtz_locksDestory();
    void qrtz_paused_trigger_grpsDestory();
    void qrtz_scheduler_stateDestory();
    void qrtz_simple_triggersDestory();
    void qrtz_simprop_triggersDestory();
    void qrtz_triggersDestory();
    void qrtz_job_detailsDestory();
    /*-----------销毁偏移量持久化 end-----------*/
}
