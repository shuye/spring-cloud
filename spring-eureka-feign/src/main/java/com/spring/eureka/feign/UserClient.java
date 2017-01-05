package com.spring.eureka.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("compute-service")
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/getByName")
    User getByName(@RequestParam(value = "name") String name);

}