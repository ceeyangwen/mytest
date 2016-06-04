package com.dang.actions.user;

import com.dang.actions.BaseAction;

public class ValidCodeAction extends BaseAction{
	private String code;
	private boolean ok=false;
	public String execute(){
//		try {
//			Thread.sleep(200);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//将code与session存储比较,根据结果设置ok属性
		String scode=(String)session.get("code");
		if(code.equals(scode)){
			ok=true;
		}else{
			ok=false;
		}
		//利用json类型的result将ok输出
		return "success";
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
}
