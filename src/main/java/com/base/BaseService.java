package com.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class BaseService {
	public  final Logger logger = LoggerFactory.getLogger(this.getClass());
}
