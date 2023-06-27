package org.prgrms.kdt;

import org.prgrms.kdt.model.entity.VoucherEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "org.prgrms.kdt")
public class AppConfig {

	@Bean
	Map<Long, VoucherEntity> getInMemoryVoucherMap() {
		return new HashMap<>();
	}
}
