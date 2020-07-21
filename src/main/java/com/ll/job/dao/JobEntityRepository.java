package com.ll.job.dao;

import com.ll.job.domain.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobEntityRepository extends JpaRepository<JobEntity, String> {
    JobEntity getByJobId(String id);
    JobEntity getByJobName(String name);
}
