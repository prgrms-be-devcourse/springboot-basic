package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
public class KdtSpringDemoApplication {
	static OutputConsole outputConsole;

	public static void main(String[] args) {
		SpringApplication.run(KdtSpringDemoApplication.class, args);

		outputConsole = new OutputConsole(new BufferedReader(new InputStreamReader(System.in)));
		outputConsole.start();


	}

}
