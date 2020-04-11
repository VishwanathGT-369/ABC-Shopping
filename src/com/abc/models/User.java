package com.abc.models;

public class User {

	private String uname;
	private String userName;
	private int uid;
	private String pword;
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}


	public String getUname() {
		return uname;
	}


	public void setUname(String uname) {
		this.uname = uname;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public int getUid() {
		return uid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getPword() {
		return pword;
	}


	public void setPword(String pword) {
		this.pword = pword;
	}


	@Override
	public String toString() {
		return "User [uname=" + uname + ", userName=" + userName + ", uid=" + uid + ", pword=" + pword + "]";
	}


	public User(String uname, String userName, int uid, String pword) {
		super();
		this.uname = uname;
		this.userName = userName;
		this.uid = uid;
		this.pword = pword;
	}
	
	

}
