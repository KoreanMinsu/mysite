package com.javaex.vo;

public class BoardVo {
//field
	private int no;
	private String title;
	private String  content;
	private int hit;
	private String reg_date;
	private int userNo;
	private String name;

//constructor	
	public BoardVo() {
		
	}
	
	public BoardVo(int no, String title, String content, int hit, String reg_date, int userNo, String name) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.reg_date = reg_date;
		this.userNo = userNo;
		this.name = name;
	}

//method-g/s
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


//method-generic	
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", reg_date="
				+ reg_date + ", userNo=" + userNo + ", name=" + name + "]";
	}

	
}	
