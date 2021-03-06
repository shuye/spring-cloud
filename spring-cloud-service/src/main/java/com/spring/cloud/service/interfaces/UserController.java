package com.spring.cloud.service.interfaces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hubay.mybatis.Page;
import com.spring.cloud.service.domain.User;
import com.spring.cloud.service.service.UserService;

@RestController
public class UserController {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/getByName", method = RequestMethod.GET)
	public User add(@RequestParam String name) {
		return userService.findByName(name);
	}
	
	@RequestMapping(value = "/queryPage", method = RequestMethod.GET)
	public Page<User> queryPage(Integer pageSize) {
		Page<User> page = new Page<User>();
		page.setPageSize(pageSize);
		return userService.queryPage(page);
	}

}