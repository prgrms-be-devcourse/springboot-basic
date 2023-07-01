package org.prgrms.kdt;

import java.util.HashMap;
import java.util.Map;

import org.prgrms.kdt.configuration.YamlPropertiesFactory;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.FileIO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan(basePackages = "org.prgrms.kdt")
@PropertySource(value = "classpath:application-dev.yml",  factory = YamlPropertiesFactory.class)
public class AppConfig {

	@Value("${repository.file.dirPath}")
	private String dirPath;

	@Value("${repository.file.fileName}")
	private String fileName;

	@Bean
	Map<Long, VoucherEntity> getInMemoryVoucherMap() {
		return new HashMap<>();
	}

	@Bean
	FileIO getFileIO() {
		return new FileIO(fileName, dirPath);
	}

	@Bean
	ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
}
