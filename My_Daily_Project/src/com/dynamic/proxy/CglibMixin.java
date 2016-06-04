package com.dynamic.proxy;

import net.sf.cglib.proxy.Mixin;

public class CglibMixin {
	
	public static void main(String[] args) {
		Class<?>[] interfaces = new Class[] {Interface1.class, Interface2.class};
		Object[] implementObjs = new Object[]{new Interface1Impl(), new Interface2Impl()};
		Object obj = Mixin.create(interfaces, implementObjs);
		Interface1 interface1 = (Interface1)obj;
		Interface2 interface2 = (Interface2)obj;
		interface1.interface1();
		interface2.interface2();
	}

}
