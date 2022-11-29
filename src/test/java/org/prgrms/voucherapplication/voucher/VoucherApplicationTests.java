package org.prgrms.voucherapplication.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucherapplication.global.config.VoucherProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("prod")
class VoucherApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${file-path.voucher}")
	String voucher;
	@Value("${file-path.blacklist}")
	String blacklist;

	@DisplayName("yaml 값 주입받기 성공")
	@Test
	void contextLoads() {
		VoucherProperties bean = applicationContext.getBean(VoucherProperties.class);

		String voucherFilePath = bean.getVoucher();
		String blacklistFilePath = bean.getBlacklist();

		assertThat(voucherFilePath).isEqualTo(voucher);
		assertThat(blacklistFilePath).isEqualTo(blacklist);
	}
}
