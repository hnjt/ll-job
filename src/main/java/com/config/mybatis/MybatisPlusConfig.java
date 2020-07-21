package com.config.mybatis;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {


    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor page = new PaginationInterceptor();
        //设置方言类型
        page.setDialectType("mysql");
        return page;
    }
    
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
    		ConfigurationCustomizer configurationCustomizer = new ConfigurationCustomizer() {
    			@Override
    			public void customize(org.apache.ibatis.session.Configuration configuration) {
    				configuration.setObjectWrapperFactory(new MapWrapperFactory());
    				configuration.setMapUnderscoreToCamelCase(true);
    				configuration.setCallSettersOnNulls(true);
    			}
    		};
    		return configurationCustomizer;
	}

}
