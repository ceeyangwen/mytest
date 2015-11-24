package com.test.rpc.consumer;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.test.rpc.model.People;
import com.test.rpc.model.SpeakInterface;

@Component("peopleConsumer")
public class PeopleConsumer {
	
	@Resource
	private SpeakInterface speakInterface;
	
	public String getSpeak(Integer age, Integer sex) {
		People people = new People();
		people.setAge(age);
		people.setSex(sex);
		return speakInterface.speak(people);
	}

}
