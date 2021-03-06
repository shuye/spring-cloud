package com.spring.cloud.service.service;

import com.hubay.mybatis.Page;
import com.spring.cloud.service.domain.User;

public interface UserService {
	
    User findByName(String name);
    
    int insert(String name,Integer age);
    
    Page<User> queryPage(Page<User> page);
}