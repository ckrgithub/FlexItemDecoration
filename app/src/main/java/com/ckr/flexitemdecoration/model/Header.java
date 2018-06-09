package com.ckr.flexitemdecoration.model;

/**
 * Created by PC大佬 on 2018/6/9.
 */

public class Header implements Cloneable{
	private String userName;
	private String firstLetter;

	public Header(String userName, String firstLetter) {
		this.userName = userName;
		this.firstLetter = firstLetter;
	}

	public String getUserName() {
		return userName;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	@Override
	public String toString() {
		return "Header{" +
				"userName='" + userName + '\'' +
				", firstLetter='" + firstLetter + '\'' +
				'}';
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
