package com.thirdpart.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jameschen.widget.CustomSelectPopupWindow.Category;

public class RetationshipDepartmentInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Category> departments;

	public List<Category> leadership,colleague,subordinate;

	public List<Category> getDepartments() {
		if (departments == null ) {
			departments = new ArrayList<Category>();
			if (leadership!=null) {
				departments.addAll(leadership);	
			}if (colleague!=null) {
				departments.addAll(colleague);	
			}if (subordinate!=null) {
				departments.addAll(subordinate);	
			}
			for (Category category : departments) {
				category.setId(category.key);
				category.setName(category.value);
			}
		}
		
		return departments;
	}
}