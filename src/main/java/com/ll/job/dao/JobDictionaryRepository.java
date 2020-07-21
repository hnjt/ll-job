package com.ll.job.dao;

import com.ll.job.domain.JobDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDictionaryRepository extends JpaRepository<JobDictionary, Integer> {

    JobDictionary getByCode(@Param( "code" )String code);
}
