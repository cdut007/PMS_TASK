package com.thirdpart.model;

public class WidgetItemInfo {

	public WidgetItemInfo(){
		
	}
	public WidgetItemInfo(String tag, String name, String content, int type,
			boolean bindClick) {
		super();
		this.tag = tag;
		this.name = name;
		this.content = content;
		this.type = type;
		this.bindClick = bindClick;
	}

	public String  tag, name,content,info,info2;
	public Object obj;
	public int  type =DISPLAY;
	
	
	public  boolean  bindClick;
	
	public static final int DISPLAY=1,EDIT=2,CHOOSE=3,DEVIDER=4,INPUT=5,ENTER=6;
}
