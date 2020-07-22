package com.ll.commons;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 业务层基类
 */
@Service
public class BaseService {

    //编辑对象
    @PersistenceContext
    protected EntityManager entityManager;
}
