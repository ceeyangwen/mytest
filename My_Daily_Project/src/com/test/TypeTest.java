package com.test;

public class TypeTest {
	
	public static void main(String[] args) {
		Value<String> value = new Value<>();
		//在Java 7中，相同的例子将不会通过编译，正确的书写方式是 Value.<String>defaultValue()。
        System.out.println(value.getOrDefault("22", Value.defaultValue()));
	}

}

class Value<T> {
	
    public static<T> T defaultValue() { 
        return null; 
    }
    
    public T getOrDefault( T value, T defaultValue ) {
        return ( value != null ) ? value : defaultValue;
    }
}