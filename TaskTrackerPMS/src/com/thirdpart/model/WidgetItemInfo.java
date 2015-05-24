package com.thirdpart.model;

public class WidgetItemInfo {

	public String  tag, name,content;
	
	public int  type =DISPLAY;
	
	
	public  boolean  bindClick;
	
	public static final int DISPLAY=1,EDIT=2,CHOOSE=3,DEVIDER=4;
}
