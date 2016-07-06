package com.test.queue;

import java.io.Serializable;

public class User implements Serializable {
	
	private String name;
	private String id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + id + "]";
	}
	
}
