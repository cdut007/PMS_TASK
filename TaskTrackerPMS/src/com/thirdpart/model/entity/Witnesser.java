package com.thirdpart.model.entity;

public class Witnesser {
	private String id;

	private String name;

	private String realname;

	private String password;

	private String email;

	private String registerTime;

	private String registerIp;

	private String currentLoginTime;

	private String currentLoginIp;

	private String lastLoginTime;

	private String lastLoginIp;

	private String loginTimes;

	private String defaultAdmin;

	private String isDefaultAdminFlag;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public String getRegisterIp() {
		return this.registerIp;
	}

	public void setCurrentLoginTime(String currentLoginTime) {
		this.currentLoginTime = currentLoginTime;
	}

	public String getCurrentLoginTime() {
		return this.currentLoginTime;
	}

	public void setCurrentLoginIp(String currentLoginIp) {
		this.currentLoginIp = currentLoginIp;
	}

	public String getCurrentLoginIp() {
		return this.currentLoginIp;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLoginTimes(String loginTimes) {
		this.loginTimes = loginTimes;
	}

	public String getLoginTimes() {
		return this.loginTimes;
	}

	public void setDefaultAdmin(String defaultAdmin) {
		this.defaultAdmin = defaultAdmin;
	}

	public String getDefaultAdmin() {
		return this.defaultAdmin;
	}

	public void setIsDefaultAdminFlag(String isDefaultAdminFlag) {
		this.isDefaultAdminFlag = isDefaultAdminFlag;
	}

	public String getIsDefaultAdminFlag() {
		return this.isDefaultAdminFlag;
	}

}