package com.spring.cloud.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubay.mybatis.Page;
import com.spring.cloud.service.domain.User;
import com.spring.cloud.service.domain.UserMapper;
import com.spring.cloud.service.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findByName(String name) {
		// TODO Auto-generated method stub
		return userMapper.findByName(name);
	}

	@Override
	public int insert(String name, Integer age) {
		// TODO Auto-generated method stub
		return userMapper.insert(name, age);
	}

	@Override
	public Page<User> queryPage(Page<User> page) {
		// TODO Auto-generated method stub
		page.setResult(userMapper.queryPage(page));
		 return page;
	}
}