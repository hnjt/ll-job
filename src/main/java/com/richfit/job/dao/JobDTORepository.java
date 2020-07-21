package com.richfit.job.dao;

import com.richfit.job.domain.JobDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDTORepository extends JpaRepository<JobDTO, String> {

}
