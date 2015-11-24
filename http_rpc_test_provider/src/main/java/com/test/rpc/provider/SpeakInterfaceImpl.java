package com.test.rpc.provider;

import org.springframework.stereotype.Component;

import com.test.rpc.model.People;
import com.test.rpc.model.SpeakInterface;

@Component("speakInterface")
public class SpeakInterfaceImpl implements SpeakInterface {

	public String speak(People people) {
		if(people.getAge() >= 18) {
			if(people.getSex() == 0) {
				return "男" + people.getAge() + "岁";
			} else {
				return "女" + people.getAge() + "岁";
			}
		} else {
			return "小朋友";
		}
	}

}
