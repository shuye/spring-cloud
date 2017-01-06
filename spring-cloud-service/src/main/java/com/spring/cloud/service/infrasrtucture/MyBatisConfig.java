package com.spring.cloud.service.infrasrtucture;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.hubay.mybatis.PagePlugin;

@Configuration  
@EnableTransactionManagement  
public class MyBatisConfig implements TransactionManagementConfigurer {  
  
    @Autowired  
    DataSource dataSource;  
  
    @Bean(name = "sqlSessionFactory")  
    public SqlSessionFactory sqlSessionFactoryBean() {  
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();  
        bean.setDataSource(dataSource);  
        //分页插件  
        PagePlugin pageHelper = new PagePlugin();  
        Properties props = new Properties();  
        props.setProperty("SQL_REGULAR", ".*?queryPage.*?");  
        props.setProperty("DIALECT", "com.hubay.mybatis.dialect.impl.MySQLDialect");  
        pageHelper.setProperties(props);  
        //添加插件  
        bean.setPlugins(new Interceptor[]{pageHelper});  
        try {
			return bean.getObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
      return null;
    }  
  
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {  
        return new SqlSessionTemplate(sqlSessionFactory);  
    }  
  
    @Bean  
    @Override  
    public PlatformTransactionManager annotationDrivenTransactionManager() {  
        return new DataSourceTransactionManager(dataSource);  
    }  
}  