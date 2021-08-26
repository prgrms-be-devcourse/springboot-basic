package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.test.OrderTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication // springboot !
@ComponentScan(basePackages = {"org.prgrms.kdt.voucher","org.prgrms.kdt.order"})//,"org.prgrms.kdt.voucher","org.prgrms.kdt.configuration"})

public class KdtApplication {
	private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

	public static void main(String[] args) {
/* spring boot */
		var applicationContext = SpringApplication.run(KdtApplication.class,args);
		var orderProperties = applicationContext.getBean(OrderProperties.class);
		logger.error("logger name => {}", logger.getName()); // messageformat 타입이랑 짬뽕됨
		logger.warn("version -> {}", orderProperties.getVersion());
		logger.debug("minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
		logger.debug("supportVendors -> {}", orderProperties.getSupportVendors());
		logger.debug("description -> {}", orderProperties.getDescription());


		/* 프로파일 강의
		var springApplication = new SpringApplication(KdtApplication.class);
		springApplication.setAdditionalProfiles("local"); // 코드말고 인텔리제이 환경설정에서 프로파일을 넣어줄 수 있음
		//나중에 jar 패키지로 배포시 Program argument로 전달해서 스프링부트를 돌리면 된다! (--spring.profiles.active=dev)

		var applicationContext= springApplication.run(args);

		//applicationContext가 반환이 된다. 따라서 똑같이 spring 처럼쓰면됨
		//ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtApplication.class, args);


		var orderProperties = applicationContext.getBean(OrderProperties.class);
		System.out.println(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
		System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", orderProperties.getMinimumOrderAmount()));
		System.out.println(MessageFormat.format("supportVendors -> {0}", orderProperties.getSupportVendors()));
		System.out.println(MessageFormat.format("description -> {0}", orderProperties.getDescription()));

		var voucherRepository = applicationContext.getBean(VoucherRepository.class);
		var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
		// Order
		var customerId = UUID.randomUUID();
		System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository instanceof JdbcVoucherRepository));
		System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository.getClass().getCanonicalName()));
*/
	}

}
