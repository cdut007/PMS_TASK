package com.thirdpart.model.entity;

import java.io.Serializable;
import java.util.List;

public class DepartmentInfo implements Serializable{
	private Department department;

	private List<Task> tasks;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}