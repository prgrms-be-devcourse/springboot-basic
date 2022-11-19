package org.programmers.program;

import org.programmers.program.io.Input;
import org.programmers.program.io.Output;
import org.programmers.program.voucher.repository.MemoryVoucherRepository;
import org.programmers.program.voucher.repository.VoucherRepository;
import org.programmers.program.voucher.service.VoucherService;
import org.springframework.boot.SpringApplication;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringVoucherApplication {
	public static void main(String[] args){
		SpringApplication.run(SpringVoucherApplication.class, args);

		Input input = new Input();
		Output output = new Output();
		VoucherRepository voucherRepository = new MemoryVoucherRepository();
		VoucherService voucherService = new VoucherService(voucherRepository);

		new VoucherProgram(input, output, voucherService, voucherRepository).run();
	}

}
