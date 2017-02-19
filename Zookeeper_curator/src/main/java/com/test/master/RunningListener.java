package com.test.master;

/**
 * 
 * @author GavinCee
 *
 */
public interface RunningListener {
	
	//启动时处理
	public void processStart(Object context);
	
	//关闭时处理
	public void processStop(Object context);
	
	public void processActiveEnter(Object context);
	
	public void processActiveExit(Object context);

}
