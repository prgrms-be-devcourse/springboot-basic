package com.programmers.springbasic.console;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputHandler {

	public <T> void printList(List<T> printableList) {
		printableList.forEach(System.out::println);
	}

	public <T> void print(T object) {
		System.out.print(object);
	}

}
