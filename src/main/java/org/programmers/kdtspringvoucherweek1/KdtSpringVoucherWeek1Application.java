package org.programmers.kdtspringvoucherweek1;

import org.programmers.kdtspringvoucherweek1.io.Console;
import org.programmers.kdtspringvoucherweek1.io.Input;
import org.programmers.kdtspringvoucherweek1.io.Output;
import org.programmers.kdtspringvoucherweek1.voucher.VoucherProperties;
import org.programmers.kdtspringvoucherweek1.voucher.repository.VoucherRepository;
import org.programmers.kdtspringvoucherweek1.voucher.VoucherManager;
import org.programmers.kdtspringvoucherweek1.voucher.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class KdtSpringVoucherWeek1Application {

	public static void main(String[] args) {
		var ac = SpringApplication.run(KdtSpringVoucherWeek1Application.class, args);
		Input input = ac.getBean(Console.class);
		Output output = ac.getBean(Console.class);
		VoucherRepository voucher = ac.getBean(VoucherRepository.class);
		VoucherService voucherService = ac.getBean(VoucherService.class);
		VoucherProperties voucherProperties = ac.getBean(VoucherProperties.class);
		new VoucherManager(input, output, voucher, voucherService, voucherProperties).run();
	}

}
