package com.programmers.voucher.core.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.programmers.voucher.console.io.ConsoleInput;
import com.programmers.voucher.console.io.ConsoleOutput;
import com.programmers.voucher.console.io.Input;
import com.programmers.voucher.console.io.Output;

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
