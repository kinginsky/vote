package com.lg.vote.model;

/**
 * @author LG
 *
 *@说明：VoterModel定义投票用户的基本信息，主要用于注册，登陆等业务的处理
 */
public class VoterModel {
	private String name=null;
	private String password=null;
	private String tablename=null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	
}
