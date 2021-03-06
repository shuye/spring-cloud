package com.hubay.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.hubay.mybatis.dialect.Dialect;
import com.hubay.mybatis.exception.DatabaseException;
import com.hubay.mybatis.help.MybatisHelper;

/**
 * 
 * @author shuye
 * @time 下午4:00:37
 */
@Intercepts({@Signature(type = Executor.class, method = "query",
args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PagePlugin implements Interceptor {
	
	private final static Logger LOG = Logger.getLogger(PagePlugin.class);
	
	protected Dialect DIALECT = null;
	
	protected String SQL_REGULAR="";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		if(statement.getId().matches(SQL_REGULAR)){
			if(LOG.isDebugEnabled()){
				LOG.info("page is begin...");
			}
			
			Object parameter = invocation.getArgs()[1];
			
			BoundSql boundSql = statement.getBoundSql(parameter);
			
			String sql = boundSql.getSql().trim();
			if(StringUtils.isBlank(sql)){
				return null;
			}
			Object parameterObj = boundSql.getParameterObject();
			if(!isPage(parameterObj)){
				throw new IllegalStateException("parameter must be page instance");
			}
			
			Connection connection = MybatisHelper.getConnection(statement);
			
			int totalCount = getTotalCount(sql,connection,statement,parameterObj,boundSql);
				
			Page<?> page = (Page<?>) parameterObj;
			page.setTotalCount(totalCount);
		//	page.setTotalPages(calculateTotalPages(page));
			String pageSql = MybatisHelper.buildPageSql(sql,page,DIALECT);
			invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
			final BoundSql bSql = MybatisHelper.newBoundSql(statement,pageSql,boundSql);
			statement = MybatisHelper.copyMappedStatement(statement,new SqlSource(){

				@Override
				public BoundSql getBoundSql(Object parameterObject) {
					return bSql;
				}
				
			});
			invocation.getArgs()[0] = statement;
			
		}
		
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
		this.DIALECT = MybatisHelper.getDialect(properties);
		
		this.SQL_REGULAR = MybatisHelper.getSqlRegular(properties);
		
	}
	
	/**
	 * get all count peple search
	 * @param sql
	 * @param connection
	 * @param statement
	 * @param parameterObj
	 * @param boundSql
	 * @return
	 */
	public int getTotalCount(final String sql,final Connection connection,
									final MappedStatement statement,
									final Object parameterObj,final BoundSql boundSql) {
		
		//final String totalCountSql = "select count(1) from (" + sql + ") as total_count";
		
	//	final String totalCountSql = "select count(1) as total_count from"+ StringUtils.substringAfter(sql, "from");
		String countSql = sql.substring(6);
		final String totalCountSql = "select count(1) as total_count from "+ getCountSql(countSql);
		
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		
		try {
			pStatement = connection.prepareStatement(totalCountSql);
			
			BoundSql bSql = new BoundSql(statement.getConfiguration(), 
										 totalCountSql, boundSql.getParameterMappings(), parameterObj);
			
			ParameterHandler handler = new DefaultParameterHandler(statement, parameterObj, bSql);
			
			handler.setParameters(pStatement);
			
			rs = pStatement.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}finally{
			MybatisHelper.freeResource(connection,rs, pStatement);
		}
		
		return 0;
	}
	
	private String getCountSql(String sql){
		sql = sql.toLowerCase();
		int indexfrom = sql.indexOf("from");
		String temp = sql.substring(0,indexfrom);
		if(temp.contains("select")){
			sql = sql.substring(indexfrom+5);
			return getCountSql(sql);
		}else{
			return sql.substring(indexfrom+5);
		}
 }
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	protected boolean isPage(Object obj){
		if(obj instanceof Page){
			return true;
		}
		return false;
	}
	
	/*private int calculateTotalPages(Page<?> page){
		int pageSize = page.getPageSize();
		DemonPredict.isTrue(pageSize>0,"pageSize must > 0");
		int totalCount = page.getTotalCount();
		int totalPages = totalCount/pageSize;
		if((totalCount%pageSize)>0)
			++totalPages;
		return totalPages;
	}*/

}
