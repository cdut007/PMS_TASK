package com.thirdpart.model.entity.base;

import java.util.List;

public class PageList <T>{

	private int pagesize;

	private int defaultStartIndex;

	private int totalCounts;

	private int pageCount;

	private int startIndex;

	private int pageNum;

	private int startPage;

	private int endPage;

	public int totalpage;
	
	private List<T> datas;

	private int currentPage;

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getPagesize() {
		return this.pagesize;
	}

	public void setDefaultStartIndex(int defaultStartIndex) {
		this.defaultStartIndex = defaultStartIndex;
	}

	public int getDefaultStartIndex() {
		return this.defaultStartIndex;
	}

	public void setTotalCounts(int totalCounts) {
		this.totalCounts = totalCounts;
	}

	public int getTotalCounts() {
		return this.totalCounts;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getStartIndex() {
		return this.startIndex;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageNum() {
		return this.pageNum;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getStartPage() {
		return this.startPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getEndPage() {
		return this.endPage;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public List<T> getDatas() {
		return this.datas;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}


}
