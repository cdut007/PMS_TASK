package com.thirdpart.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.R.menu;

public class IssueMenu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String content;
	public  int count;
	
	public String getId() {
		return id;
	}

	public static List<IssueMenu> getMineMenus(boolean showPlan) {
		List<IssueMenu> menus = new ArrayList<IssueMenu>();
		menus.add(new IssueMenu("0", "我的问题"));
		if (showPlan) {
			menus.add(new IssueMenu("1", "我的计划"));
		}

		menus.add(new IssueMenu("2", "我的见证"));
		return menus;
	}

	public static IssueMenu getPlan() {
		// TODO Auto-generated method stub
		return new IssueMenu("1", "我的计划");
	}

	public static IssueMenu getIssue(String category) {
		// TODO Auto-generated method stub
		// solve >>需要解决的问题
		// concern>> 需要关注的问题
		// confirm>>需要确认的问题
		switch (category) {
		case "solve":

			return new IssueMenu("1", "需要解决问题");
		case "concern":

			return new IssueMenu("5", "关注的问题");
		case "confirm":

			return new IssueMenu("4", "需要确认的问题");

		}
		return null;
	}

	public static List<IssueMenu> getMenus() {
		List<IssueMenu> menus = new ArrayList<IssueMenu>();
		menus.add(new IssueMenu("1", "需要处理问题"));
		menus.add(new IssueMenu("2", "已解决问题"));
		menus.add(new IssueMenu("0", "未解决问题"));
		menus.add(new IssueMenu("3", "发起的问题"));
		menus.add(new IssueMenu("4", "需要确认的问题"));
		menus.add(new IssueMenu("5", "关注的问题"));
		return menus;
	}

	public static List<IssueMenu> getWitnessMenus() {
		List<IssueMenu> menus = new ArrayList<IssueMenu>();
		menus.add(new IssueMenu("0", "收到的见证"));
		menus.add(new IssueMenu("1", "发起的见证"));
		menus.add(new IssueMenu("2", "已分派的见证"));
		
		return menus;
	}

	public IssueMenu(String id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}