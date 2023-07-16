package org.prgrms.kdt;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.prgrms.kdt.configuration.YamlPropertiesFactory;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.util.FileIO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = "org.prgrms.kdt")
@PropertySource(value = "classpath:application-dev.yml", factory = YamlPropertiesFactory.class)
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

	@Bean
	public DataSource dataSource() throws SQLException {
		var dataSource = DataSourceBuilder.create()
			.url("jdbc:mysql://localhost/order_mgmt")
			.username("root")
			.password("root1234!")
			.type(HikariDataSource.class)
			.build();

		dataSource.setMaximumPoolSize(100);
		dataSource.setMinimumIdle(10);

		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
		return new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
