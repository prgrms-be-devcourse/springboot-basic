package org.programmers.spbw1;

import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;
import org.programmers.spbw1.voucher.MemoryVoucherRepository;
import org.programmers.spbw1.voucher.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
@SpringBootApplication
public class SbpW1Application {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SbpW1Application.class, args);
		System.out.println("안녕!");

		Input input = new Console();
		Output output = new Console();
		VoucherRepository repository = new MemoryVoucherRepository();

		new VoucherProgram(input, output, repository).run();
	}


}
