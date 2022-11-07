package org.programmers.spbw1;

import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class SbpW1Application {
	static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	static Output output = new Console();
	static Input input = new Console();
	// static Logger logger = new LoggerFa

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SbpW1Application.class, args);
		System.out.println("hello");
		while(true){
			output.initSelect();
			String in = input.input("");
			if (in.equals("exit"))
				break;
			System.out.println(in);
		}
	}

}
