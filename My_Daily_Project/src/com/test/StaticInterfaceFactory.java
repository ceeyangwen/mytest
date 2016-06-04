package com.test;

import java.util.function.Supplier;

public interface StaticInterfaceFactory {
	
	static DefaultInterface create(Supplier<DefaultInterface> supplier) {
		return supplier.get();
	}

}
