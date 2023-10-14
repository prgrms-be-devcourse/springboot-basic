package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.VoucherFunction;
import org.prgrms.kdtspringdemo.voucher.controller.VoucherController;
import org.prgrms.kdtspringdemo.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class KdtSpringDemoApplication {
	static InputConsole inputConsole;
	static OutputConsole outputConsole;
	static VoucherController voucherController;
	static Logger logger = LoggerFactory.getLogger(KdtSpringDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KdtSpringDemoApplication.class, args);

		inputConsole = new InputConsole(new BufferedReader(new InputStreamReader(System.in)));
		outputConsole = new OutputConsole();
		VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());
		voucherController = new VoucherController(voucherService, inputConsole, outputConsole);

		try {
			while (true) {
				outputConsole.start();
				String fun = inputConsole.getString();
				try {
					VoucherFunction.findByCode(fun).execute(voucherController);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

}
