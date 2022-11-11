package org.prgrms.voucherapplication;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucherapplication.config.VoucherProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class VoucherApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	final String description = "=== Voucher Program ===\n" +
			"Type exit to exit the program.\n" +
			"Type create to create a new voucher.\n" +
			"Type list to list all vouchers.";

	@DisplayName("yaml 값 주입받기 성공")
	@Test
	void contextLoads() {
		VoucherProperties bean = applicationContext.getBean(VoucherProperties.class);

		String description = bean.getDescription();

		Assertions.assertThat(description).isEqualTo(description);
	}
}
