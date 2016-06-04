package com.test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class MethodReferenceTest {
	
	public static MethodReferenceTest create(Supplier<MethodReferenceTest> supplier) {
		return supplier.get();
	}
	
	public void method1(MethodReferenceTest another) {
        System.out.println( "method1 : " + another.toString() );
    }
	
	public void method2() {
        System.out.println( "method2 : " + this.toString() );
    }
	
	public static void method3(MethodReferenceTest another) {
		System.out.println( "method3 : " + another.toString() );
	}
	
	public static void main(String[] args) {
		//第一种方法引用是构造器引用，它的语法是Class::new
		MethodReferenceTest test1 = create(MethodReferenceTest::new);
		List<MethodReferenceTest> lists = Arrays.asList(test1);
		//第二种引用是静态方法引用，它的语法是Class::static_method
		lists.forEach(MethodReferenceTest::method3);
		//第三种方法引用是特定类的任意对象的方法引用，它的语法是Class::method。
		lists.forEach(MethodReferenceTest::method2);
		//第四种方法引用是特定对象的方法引用，它的语法是instance::method
		MethodReferenceTest test2 = create(MethodReferenceTest::new);
		lists.forEach(test2::method1);
	}

}
