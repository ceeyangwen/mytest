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
		//��code��session�洢�Ƚ�,���ݽ������ok����
		String scode=(String)session.get("code");
		if(code.equals(scode)){
			ok=true;
		}else{
			ok=false;
		}
		//����json���͵�result��ok���
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
