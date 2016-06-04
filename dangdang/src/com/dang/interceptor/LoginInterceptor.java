package com.dang.interceptor;

import java.util.Map;

import com.dang.utils.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation arg0) throws Exception {
		//�ж��Ƿ��¼
		Map<String,Object> session=ActionContext.getContext().getSession();
		if(session.get(Constant.USER)==null){
			//δ��¼
			return "login";
		}else{
			String resultCode=arg0.invoke();
			return resultCode;
		}
	}

}
