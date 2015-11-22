package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.helper.DatabaseHelper;

/**
 * 事务代理类
 * @author GavinCee
 *
 */
public class TransactionProxy implements Proxy{

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);
	
	//标志，用于保证同一线程中事务控制的相关逻辑只会执行一次
	private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
		protected Boolean initialValue() {
			return false;
		};
	};

	//通过ProxyChain对象获取目标方法，判断该方法是否带有Transaction注解，调用开始事务，然后执行方法，再关闭事务，或者在异常中回滚事务
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		boolean flag = FLAG_HOLDER.get();
		Method method = proxyChain.getTargetMethod();
		if(!flag && method.isAnnotationPresent(Transaction.class)) {
			FLAG_HOLDER.set(true);
			try { 
				DatabaseHelper.beginTransaction();
				LOGGER.info("begin Transaction");
				result = proxyChain.doProxyChain();
				DatabaseHelper.commitTransaction();
				LOGGER.debug("commit transaction");
			} catch (Exception e) {
				DatabaseHelper.rollbackTransaction();
				LOGGER.error("rollback transaction", e);
				throw new RuntimeException(e);
			} finally {
				FLAG_HOLDER.remove();
			}
		} else {
			result = proxyChain.doProxyChain();
		}
		return result;
	}
	
	
	
}
