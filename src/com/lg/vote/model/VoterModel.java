package com.lg.vote.model;

/**
 * @author LG
 *
 *@˵����VoterModel����ͶƱ�û��Ļ�����Ϣ����Ҫ����ע�ᣬ��½��ҵ��Ĵ���
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
