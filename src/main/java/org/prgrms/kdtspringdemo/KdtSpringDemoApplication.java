package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.VoucherFunction;
import org.prgrms.kdtspringdemo.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class KdtSpringDemoApplication {
	static InputConsole inputConsole;
	static OutputConsole outputConsole;
	static VoucherService voucherService;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(KdtSpringDemoApplication.class, args);

		inputConsole = new InputConsole(new BufferedReader(new InputStreamReader(System.in)));
		outputConsole = new OutputConsole();
		voucherService = new VoucherService(new MemoryVoucherRepository(), inputConsole, outputConsole);

		while(true) {
			outputConsole.start();
			String fun = inputConsole.getString();
			try {
				VoucherFunction.findByCode(fun).execute(voucherService);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

}
