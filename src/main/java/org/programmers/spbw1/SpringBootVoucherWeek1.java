package org.programmers.spbw1;

import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;
import org.programmers.spbw1.voucher.Repository.MemoryVoucherRepository;
import org.programmers.spbw1.voucher.Repository.VoucherRepository;
import org.programmers.spbw1.voucher.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
@SpringBootApplication
public class SpringBootVoucherWeek1 {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SpringBootVoucherWeek1.class, args);
		System.out.println("안녕!");

		Input input = new Console();
		Output output = new Console();
		VoucherRepository repository = new MemoryVoucherRepository();
		VoucherService service = new VoucherService(repository);

		new VoucherProgram(input, output, repository, service).run();
	}

}
