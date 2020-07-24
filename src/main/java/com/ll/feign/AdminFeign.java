package com.ll.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name = "ll-admin")
public interface AdminFeign {

    /**
     * feign 接口注入方式
     * private FeignService feignService;
     *
     *     @Autowired
     *     public void setFeignService(FeignService feignService) {
     *         this.feignService = feignService;
     *     }
     */

//    @GetMapping(value = "/job/job/findAllDictionary")
//    String findAllDictionary();
//
//    @GetMapping(value = "/job/job/getJob")
//    String getJob(@RequestParam String id);
}
