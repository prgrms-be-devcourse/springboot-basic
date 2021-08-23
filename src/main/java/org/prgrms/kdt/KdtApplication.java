package org.prgrms.kdt;

import org.prgrms.kdt.order.property.OrderProperties;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.JDBCVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.prgrms.kdt.order",
		"org.prgrms.kdt.voucher",
		"org.prgrms.kdt.configuration",
		"org.prgrms.kdt.command"
})
public class KdtApplication {

	public static void main(String[] args) {
		var springApplication = new SpringApplication(KdtApplication.class);
//		springApplication.setAdditionalProfiles("local");
		var applicationContext = springApplication.run(args);

		var orderProperties = applicationContext.getBean(OrderProperties.class);

        System.out.println(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
        System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", orderProperties.getMinimumOrderAmount()));
        System.out.println(MessageFormat.format("supportVendeors -> {0}", orderProperties.getSupportVendors()));
        System.out.println(MessageFormat.format("description -> {0}", orderProperties.getDescription()));

		var customerId = UUID.randomUUID();

		// bean에서 꺼내올대도 BeanFactoryAnnotationUtils에서 적합한 용도를 가진 Bean을 선택할 수 있습니다.
		var voucherRepository = applicationContext.getBean(VoucherRepository.class);
		var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
		// System.out.println(MessageFormat.format("voucher -> {0}", voucherRepository.find()));

		System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository instanceof JDBCVoucherRepository));
		System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository.getClass().getCanonicalName()));


	}

}
