package org.prgrms.kdt;

import java.util.HashMap;
import java.util.Map;

import org.prgrms.kdt.model.entity.Voucher;
import org.prgrms.kdt.model.repository.FileIO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan(basePackages = "org.prgrms.kdt")
public class AppConfig {

	@Bean
	Map<Long, Voucher> getInMemoryVoucherMap() {
		return new HashMap<>();
	}

	@Bean
	FileIO getFileIO(){ return new FileIO(); }

	@Bean
	ObjectMapper getObjectMapper() { return new ObjectMapper(); }
}
