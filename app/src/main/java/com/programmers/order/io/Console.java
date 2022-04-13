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
	private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

	@Override
	public String read(BasicMessage message){
		this.write(message);
		return input.nextLine();
	}

	@Override
	public void write(BasicMessage message){
		System.out.println(message.toString());
	}

	@Override
	public void write(ErrorMessage message){
		System.out.println(message.toString());
	}
}
