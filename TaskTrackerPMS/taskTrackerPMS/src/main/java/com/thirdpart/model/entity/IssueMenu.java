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
	
	public String tag;
	
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
		menus.add(new IssueMenu("3", "已完成的见证"));
		return menus;
	}
	
	public static List<IssueMenu> getWitnessMenusByNameA_to_D(String menu,String name) {
		List<IssueMenu> menus = new ArrayList<IssueMenu>();
		menus.add(new IssueMenu(menu,"QC1", "我"+name+"的见证QC1"));
		menus.add(new IssueMenu(menu,"QC2", "我"+name+"的见证QC2"));
		menus.add(new IssueMenu(menu,"A", "我"+name+"的见证A"));
		menus.add(new IssueMenu(menu,"B", "我"+name+"的见证B"));
		menus.add(new IssueMenu(menu,"C", "我"+name+"的见证C"));
		menus.add(new IssueMenu(menu,"D", "我"+name+"的见证D"));
		return menus;
	}
	
	public IssueMenu(String id, String tag,String content) {
		this(id,content);
		this.tag = tag;
		
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