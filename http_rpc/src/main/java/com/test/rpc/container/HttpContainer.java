package com.test.rpc.container;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;

import com.test.rpc.invoke.ProviderConfig;

public class HttpContainer extends Container {
	
	private static final Logger logger = Logger.getLogger(HttpContainer.class);
	
	private AbstractHandler abstractHandler;
	
	private ProviderConfig providerConfig;
	
	public HttpContainer(AbstractHandler handler) {
		this(handler, new ProviderConfig("/invoke", 8080));
	}
	
	public HttpContainer(AbstractHandler handler, ProviderConfig config) {
		this.abstractHandler = handler;
		this.providerConfig = config;
		Container.container = this;
	}

	@Override
	public void start() {	
		Server server = new Server();
		try {
			SelectChannelConnector connector = new SelectChannelConnector();
			connector.setPort(providerConfig.getPort());
			server.setConnectors(new Connector[]{connector});
			server.setHandler(abstractHandler);
			server.start();
			isStart = true;
		} catch (Exception e) {
			logger.error("容器异常", e);
		}
	}

}
