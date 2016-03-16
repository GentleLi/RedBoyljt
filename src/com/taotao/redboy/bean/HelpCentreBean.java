package com.taotao.redboy.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

@Table(name="helpcentre")
public class HelpCentreBean {
	
	public HelpCentreBean(long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	public HelpCentreBean() {
		super();
	}
	@NoAutoIncrement
	@Column(column="_id")
	public long id;
	
	@Column(column="title")
	public String title;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
}
