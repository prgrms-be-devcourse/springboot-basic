package org.prgms.springbootbasic;

import org.prgms.springbootbasic.voucher.io.Console;
import org.prgms.springbootbasic.voucher.io.Output;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBasicApplication.class, args);
		Output output = new Console();
		output.printProgramManual();
	}

}
