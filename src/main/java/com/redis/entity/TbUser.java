package com.redis.entity;

public class TbUser {

	public static String TABLE = "TB_USER";

	public int USER_ID;
	public String USER_NAME;
	public String USER_PWD;
	public String USER_PHONE;
	public int USER_AGE;

	public int getUserId() {
		return USER_ID;
	}

	public void setUserId(int userId) {
		this.USER_ID = userId;
	}

	public String getUserName() {
		return USER_NAME;
	}

	public void setUserName(String userName) {
		this.USER_NAME = userName;
	}

	public int getUserAge() {
		return USER_AGE;
	}

	public void setUserAge(int userAge) {
		this.USER_AGE = userAge;
	}

	public String getUserPhone() {
		return USER_PHONE;
	}

	public void setUserPhone(String userPhone) {
		this.USER_PHONE = userPhone;
	}

	public String getUserPwd() {
		return USER_PWD;
	}

	public void setUserPwd(String userPwd) {
		this.USER_PWD = userPwd;
	}
}
