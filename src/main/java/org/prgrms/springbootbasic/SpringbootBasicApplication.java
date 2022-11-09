package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.factory.VoucherFactory;
import org.prgrms.springbootbasic.repository.VoucherRepository;
import org.prgrms.springbootbasic.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@SpringBootApplication
public class SpringbootBasicApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SpringbootBasicApplication.class, args);
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication
				.run(SpringbootBasicApplication.class, args);
//				.getBean();
//		.process();
		Console console = configurableApplicationContext.getBean(Console.class);
		console.run();

//		VoucherService voucherService = configurableApplicationContext.getBean(VoucherService.class);

//		Map<String, VoucherFactory> map = voucherService.getVoucherFactoryMap();
//		System.out.println(map.get(TypeOption.FIXED.getBeanName()));
//		System.out.println(map.get("fixexAmountVou"));

	}

}
