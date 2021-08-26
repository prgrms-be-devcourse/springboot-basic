package org.prgrms.kdt;

import org.prgrms.kdt.Customers.CustomersService;
import org.prgrms.kdt.Voucher.VoucherApplication;
import org.prgrms.kdt.Voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KdtApplication {
	private static  final Logger logger= LoggerFactory.getLogger(KdtApplication.class);

	public static void main(String[] args) {


		SpringApplication springApplication = new SpringApplication(KdtApplication.class);
		springApplication.setAdditionalProfiles("prod");
		var applicationContext = springApplication.run(args);

		var voucherService = applicationContext.getBean(VoucherService.class);
		var customersService=applicationContext.getBean(CustomersService.class);
		new VoucherApplication().run(voucherService,customersService);




	}

}
