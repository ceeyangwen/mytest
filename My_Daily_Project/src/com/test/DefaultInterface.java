package com.test;

public interface DefaultInterface {
	
	default String defaultMethod() {
		return "Default Method";
	}

}

class DeafaultImpl implements DefaultInterface {
	
}

class DefaultOverride implements DefaultInterface {
	@Override
	public String defaultMethod() {
		return "Override Method";
	}
	
}
