package org.programmers.voucherProgram;

import org.programmers.voucherProgram.io.Input;
import org.programmers.voucherProgram.io.Output;
import org.programmers.voucherProgram.voucher.Repository.MemoryVoucherRepository;
import org.programmers.voucherProgram.voucher.Repository.VoucherRepository;
import org.programmers.voucherProgram.voucher.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
@SpringBootApplication
public class SpringBootVoucherWeek1 {

	public static void main(String[] args){
		SpringApplication.run(SpringBootVoucherWeek1.class, args);

		new VoucherProgram().run();
	}

}
