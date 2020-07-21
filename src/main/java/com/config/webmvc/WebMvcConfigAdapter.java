/**
 * 文件名：IndexAdapter.java
 * 
 *北京中油瑞飞信息技术有限责任公司(http://www.richfit.com)
 * Copyright © 2017 Richfit Information Technology Co., LTD. All Right Reserved.
 */
package com.config.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p>
 * <li>Description:</li>
 * <li>$Author$</li>
 * <li>$Revision: 4547 $</li>
 * <li>$Date: 2017-05-12 16:10:40 +0800 (Fri, 12 May 2017) $</li>
 * 
 * @version 1.0
 */
@Configuration
public class WebMvcConfigAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/doc.html");
        registry.addViewController("/doc").setViewName("redirect:/doc.html");
        registry.addViewController("/doc/").setViewName("redirect:/doc.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

		// TODO Auto-generated method stub

    
}
