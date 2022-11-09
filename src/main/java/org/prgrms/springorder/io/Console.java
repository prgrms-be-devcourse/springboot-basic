package org.prgrms.springorder.io;

import java.util.List;
import java.util.Scanner;

import org.prgrms.springorder.domain.Message;
import org.springframework.stereotype.Component;

@Component
public class Console implements Input, Output{

	private final Scanner scanner = new Scanner(System.in);

	@Override
	public String read(Message m) {
		System.out.print(m);
		return scanner.nextLine();
	}

	@Override
	public void write(String s) {
		System.out.println(s);
	}
	@Override
	public void write(Message m) {
		System.out.println(m);
	}

	@Override
	public void writeList(List<String> list) {
		for (String s : list) {
			System.out.println(s);
		}
		System.out.println();
	}
}
