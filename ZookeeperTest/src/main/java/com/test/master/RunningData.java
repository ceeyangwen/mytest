package com.test.master;

import java.io.Serializable;

/**
 * 节点数据
 * @author GavinCee
 *
 */
public class RunningData implements Serializable {

	private static final long serialVersionUID = 4444553271141491405L;
	
	private Long cid;
	private String name;
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
