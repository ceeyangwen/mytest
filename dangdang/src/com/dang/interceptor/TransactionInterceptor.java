package com.dang.interceptor;

import com.dang.utils.DBUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class TransactionInterceptor extends AbstractInterceptor{
	public String intercept(ActionInvocation invocation) throws Exception {
		//��������Ϊ�ֶ��ύ
		try{
			DBUtils.getConnection().setAutoCommit(false);
			String resultCode=invocation.invoke();
			//�ύ����
			DBUtils.getConnection().commit();
			return resultCode;
		}catch(Exception e){
			DBUtils.getConnection().rollback();
			e.printStackTrace();
			return "fail";
		}finally{
			DBUtils.closeConnection();
		}
	}
	
}
