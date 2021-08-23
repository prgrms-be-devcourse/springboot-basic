package org.prgrms.kdt;

import org.prgrms.kdt.customer.controller.CustomerController;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.order.OrderTester;
import org.prgrms.kdt.voucher.controller.VoucherController;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.customer"})
public class KdtApplication {

	public static void main(String[] args) {
		// app context 등록
		var springApplication = new SpringApplication(KdtApplication.class);
		springApplication.setAdditionalProfiles("prod");
		var applicationContext = springApplication.run(args);

		// customer -> black list 불러오기 (컨트롤러 생성자에서 출력하므로 생성만함)
		new CustomerController(applicationContext.getBean(CustomerService.class));

		// Voucher 관리 controller 구동
		VoucherController voucherController = new VoucherController(applicationContext.getBean(VoucherService.class));
		voucherController.run();

		// AppContext 종료
		applicationContext.close();
	}

}
