package com.programmers.voucher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.programmers.voucher.io.ConsoleInput;
import com.programmers.voucher.io.ConsoleOutput;
import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Output;

@Configuration
public class AppConfig {

	@Bean
	public Input input() {
		return new ConsoleInput(new BufferedReader(new InputStreamReader(System.in)));
	}

	@Bean
	public Output output() {
		return new ConsoleOutput(new BufferedWriter(new OutputStreamWriter(System.out)));
	}
}
