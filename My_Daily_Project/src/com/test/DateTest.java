package com.test;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZonedDateTime;

public class DateTest {
	
	public static void main(String[] args) {
		testClock();
		testLocalDateTime();
		testDuration();
	}
	
	public static void testClock() {
		final Clock clock = Clock.systemUTC();
		System.out.println( clock.instant() );
		System.out.println( clock.millis() );
	}
	
	public static void testLocalDateTime() {
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		LocalDateTime datetime = LocalDateTime.now();
		ZonedDateTime zonedDatetime = ZonedDateTime.now();//获取特定的日期与时区
		System.out.println(date);
		System.out.println(time);
		System.out.println(datetime);
		System.out.println(zonedDatetime);
	}
	
	//Duration类：在秒与纳秒级别上的一段时间。Duration使计算两个日期间的不同变的十分简单。
	public static void testDuration() {
		LocalDateTime from = LocalDateTime.of( 2015, Month.MAY, 16, 0, 0, 0 );
		LocalDateTime to = LocalDateTime.of( 2016, Month.MAY, 16, 23, 59, 59 );
		Duration duration = Duration.between( from, to );
		System.out.println( "Duration in days: " + duration.toDays() );
		System.out.println( "Duration in hours: " + duration.toHours() );
	}

}
