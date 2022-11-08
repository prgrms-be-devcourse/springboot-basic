package org.programmers.spbw1;

import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;
import org.programmers.spbw1.voucher.MemoryVoucherRepository;
import org.programmers.spbw1.voucher.VoucherRepository;
import org.programmers.spbw1.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@SpringBootApplication
public class SbpW1Application {
	// static final Logger logger = LoggerFactory.getLogger(SbpW1Application.class);

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SbpW1Application.class, args);
		System.out.println("hello");
//		while(true){
//			output.initSelect();
//			String in = input.input("");
//			if (in.equals("exit"))
//				break;
//			System.out.println(in);
//		}
		Input input = new Console();
		Output output = new Console();
		VoucherRepository repository = new MemoryVoucherRepository();
		// VoucherService service = new VoucherService(repository);

		new VoucherProgram(input, output, repository).run();
	}


}
