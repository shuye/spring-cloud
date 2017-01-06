package com.spring.eureka.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hubay.lang.Page;

@RestController
public class ConsumerController {

    @Autowired
    ComputeClient computeClient;
    @Autowired
    UserClient userClient;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add() {
        return computeClient.add(10, 20);
    }
    
    @RequestMapping(value = "/getByName", method = RequestMethod.GET)
    public User getByName(String name) {
        return userClient.getByName(name);
    }
    
    @RequestMapping(value = "/queryPage", method = RequestMethod.GET)
    public Page<User> queryPage(Integer pageSize) {
        return userClient.queryPage(pageSize);
    }
    
    

}