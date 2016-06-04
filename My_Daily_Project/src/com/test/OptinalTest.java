package com.test;

import java.util.Optional;

public class OptinalTest {
	
	public static void main(String[] args) {
		testOptional(null);
		testOptional("Tom");
	}
	
	public static void testOptional(String name) {
		Optional< String > firstName = Optional.ofNullable(name);
		System.out.println( "First Name is set? " + firstName.isPresent() );        
		System.out.println( "First Name: " + firstName.orElseGet( () -> "[none]" ) ); 
		System.out.println( firstName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );
		System.out.println();
	}

}
