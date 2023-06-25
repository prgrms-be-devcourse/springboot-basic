package org.devcourse.springbasic;

import org.devcourse.springbasic.config.VoucherConfig;
import org.devcourse.springbasic.io.ConsoleDevice;
import org.devcourse.springbasic.io.IODevice;
import org.devcourse.springbasic.voucher.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBasicApplication {

	public static void main(String[] args) {

		try {

			SpringApplication springApplication = new SpringApplication(SpringBasicApplication.class, VoucherConfig.class);
			ConfigurableApplicationContext ac = springApplication.run(args);

			VoucherService voucherService = ac.getBean(VoucherService.class);

			IODevice consoleDevice = new ConsoleDevice();
			VoucherApplication voucherApplication = new VoucherApplication(consoleDevice, voucherService);
			voucherApplication.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
