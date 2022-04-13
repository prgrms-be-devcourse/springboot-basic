package com.programmers.order.io;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;

@Component
public class Console implements Input, Output {

	private static final Scanner input = new Scanner(System.in);

	@Override
	public String read(BasicMessage message){
		this.write(message);
		return input.nextLine();
	}

	@Override
	public void write(BasicMessage message){
		System.out.println(message.send());
	}

	@Override
	public void write(ErrorMessage message){
		System.out.println(message.send());
	}
}
