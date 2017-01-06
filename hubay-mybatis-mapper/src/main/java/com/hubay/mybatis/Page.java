package com.hubay.mybatis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.hubay.mybatis.help.DemonPredict;

/**
 * 
 * @author shuye
 * @time 下午3:55:10
 */
public class Page<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the number of very page
	 */
	private int pageSize = 20;
	
	/**
	 * which page we hold now
	 */
	private int currentPage = 1;
	
	/**
	 * how many pages we will get
	 */
	private int totalPages = 0;
	
	/**
	 * how many counts we will get
	 */
	private int totalCount=0;
	
	/**
	 * count the total number or not
	 */
	private boolean totalCountAble = true;
	
	/**
	 * the query result,what we want to get
	 */
	private List<T> result = Lists.newArrayList();
	/**
	 * the params for query
	 */
	private Map<String,Object> params = new HashMap<String, Object>();
	
    private Map<String,Sort> sorts = new HashMap<String,Sort>();

	public int getPageSize() {
		return pageSize;
	}

	public Page<T> setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public Page<T> setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		return this;
	}

	public int getNextPage() {
		if(hasNextPage())
			return currentPage+1;
		return currentPage;
	}

	public int getPrePage() {
		if(hasPrePage())
			return currentPage-1;
		return currentPage;
	}

	
	public int getTotalPages() {
		int pageSize = getPageSize();
		DemonPredict.isTrue(pageSize>0,"pageSize must > 0");
		int totalCount = getTotalCount();
		int totalPages = totalCount/pageSize;
		if((totalCount%pageSize)>0)
			++totalPages;
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public  int getFirstPage(){
		return 1;
	}
	public int getFirst() {
		return ((currentPage - 1) * pageSize) + 1;
	}
	
	public int getLastPage(){
		return getTotalPages();
	}
	
	public boolean hasPrePage(){
		return currentPage>1;
	}
	
	public boolean hasNextPage(){
		return totalPages>currentPage;
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public Page<T> setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		return this;
	}


	public List<T> getResult() {
		return result;
	}

	public Page<T> setResult(List<T> result) {
		this.result = result;
		return this;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public Page<T> setParams(Map<String, Object> params) {
		this.params = params;
		return this;
	}
	
	public Page<T> addParam(String key,Object value){
		params.put(key, value);
		return this;
	}
	
	public Page<T> addParams(Map<String,Object> _params){
		params.putAll(_params);
		return this;
	}
	
	 /**
     * 
     * @return
     */
    public Map<String, Sort> getSorts() {
		return sorts;
	}
	
	  /**
     * @param sorts
     */
	public Page<T> setSorts(Map<String, Sort> sorts) {
		this.sorts = sorts;
		return this;
	}
	
	public Page<T> addSorts(String field,Sort sort){
		this.sorts.put(field, sort);
		return this;
	}
	
	public boolean isTotalCountAble() {
		return totalCountAble;
	}

	public Page<T> setTotalCountAble(boolean totalCountAble) {
		this.totalCountAble = totalCountAble;
		return this;
	}
	/**
	 * calculate how much result this query
	 * @return
	 */
	public Page<T> calculateTotalNumber(){
		setTotalCountAble(true);
		return this;
	}
	/**
	 * no calculate how much result this query
	 * @return
	 */
	public Page<T> notCalculateTotalNumber(){
		setTotalCountAble(false);
		return this;
	}
	
	/**
	 * @return
	 */
	public static <T> Page<T> newPage(){
		return new Page<T>();
	}

	/**
     * @author shuye
     *
     */
    public enum Sort{
    	ASC("asc"),DESC("desc");
    	
    	private String sort="desc";
    	
    	private Sort(String sort){
    		this.sort = sort;
    	}
    	
    	public String sort(){
    		return sort;
    	}
    }
	
}
