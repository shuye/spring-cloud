package com.spring.cloud.service.domain;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hubay.mybatis.Page;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE name = #{name}")
    User findByName(@Param("name") String name);
    
    @Insert("INSERT INTO user(name, age) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);
    
    @Select("SELECT * FROM user ")
    List<User> queryPage(Page<User> page);
}