package com.netty.chapter12.codec;

import java.io.IOException;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

/**  
 * @author GavinCee  
 * @date 2016年9月11日  
 *
 */
public class MarshallingCodecFactory {
	
	/**
	 * 创建Jboss Marshaller
	 * @return
	 * @throws IOException
	 */
	public static Marshaller buildMarshalling() throws IOException {
		final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration config = new MarshallingConfiguration();
		config.setVersion(5);
		Marshaller marshaller = factory.createMarshaller(config);
		return marshaller;
	}
	
	public static Unmarshaller buildUnmarshalling() throws IOException {
		final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration config = new MarshallingConfiguration();
		config.setVersion(5);
		Unmarshaller unmarshaller = factory.createUnmarshaller(config);
		return unmarshaller;
	}

}
