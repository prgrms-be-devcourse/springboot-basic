package org.prgrms.kdt;

import java.util.HashMap;
import java.util.Map;

import org.prgrms.kdt.model.entity.Voucher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.prgrms.kdt")
public class AppConfig {

	@Bean
	Map<Long, Voucher> getInMemoryVoucherMap() {
		return new HashMap<>();
	}
}
