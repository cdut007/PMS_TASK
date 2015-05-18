package com.thirdpart.model.entity.base;

import java.util.List;

public class PageList <T>{

	private String pagesize;

	private String defaultStartIndex;

	private String totalCounts;

	private String pageCount;

	private String startIndex;

	private String pageNum;

	private String startPage;

	private String endPage;

	private List<T> datas;

	private String currentPage;

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getPagesize() {
		return this.pagesize;
	}

	public void setDefaultStartIndex(String defaultStartIndex) {
		this.defaultStartIndex = defaultStartIndex;
	}

	public String getDefaultStartIndex() {
		return this.defaultStartIndex;
	}

	public void setTotalCounts(String totalCounts) {
		this.totalCounts = totalCounts;
	}

	public String getTotalCounts() {
		return this.totalCounts;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getPageCount() {
		return this.pageCount;
	}

	public void setStartIndex(String startIndex) {
		this.startIndex = startIndex;
	}

	public String getStartIndex() {
		return this.startIndex;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageNum() {
		return this.pageNum;
	}

	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}

	public String getStartPage() {
		return this.startPage;
	}

	public void setEndPage(String endPage) {
		this.endPage = endPage;
	}

	public String getEndPage() {
		return this.endPage;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public List<T> getDatas() {
		return this.datas;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getCurrentPage() {
		return this.currentPage;
	}


}
