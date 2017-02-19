package com.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Enumeration;

/**  
 * @author GavinCee  
 * @date 2016年10月22日  
 *
 */
public class Test {
	
	public static void main(String[] args) throws SocketException {
		StringBuilder strBuilder = new StringBuilder();
		Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
		while(e.hasMoreElements()) {
			NetworkInterface ni = e.nextElement();
			strBuilder.append(ni.toString());
			byte[] mac = ni.getHardwareAddress();
			if(mac != null) {
				ByteBuffer buf = ByteBuffer.wrap(mac);
				strBuilder.append(buf.getChar());
				strBuilder.append(buf.getChar());
				strBuilder.append(buf.getChar());
			}
		}
		System.out.println(strBuilder.toString());
	}

}
