package com.programmers.order.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.programmers.order.manager.FixedVoucherManager;
import com.programmers.order.manager.PercentVoucherManager;
import com.programmers.order.manager.VoucherClientManager;
import com.programmers.order.manager.VoucherInternalManager;
import com.programmers.order.provider.VoucherProvider;
import com.programmers.order.repository.CustomerJdbcRepository;
import com.programmers.order.repository.CustomerRepository;
import com.programmers.order.repository.VoucherJdbcRepositoryImpl;
import com.programmers.order.repository.VoucherRepository;
import com.zaxxer.hikari.HikariDataSource;

@TestConfiguration
public class TestJdbcConfig {

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder
				.create()
				.url("jdbc:mysql://localhost:3306/mission_order?serverTimezone=Asia/Seoul")
				.username("root")
				.password("")
				.type(HikariDataSource.class)
				.build();
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	public PercentVoucherManager percentVoucherManager() {
		return new PercentVoucherManager();
	}

	public FixedVoucherManager fixedVoucherManager() {
		return new FixedVoucherManager();
	}

	@Bean
	public List<VoucherInternalManager> voucherInternalManager() {
		return List.of(percentVoucherManager(), fixedVoucherManager());
	}

	@Bean
	public List<VoucherClientManager> voucherClientManager() {
		return List.of(fixedVoucherManager(), percentVoucherManager());
	}

	@Bean
	public VoucherProvider voucherProvider() {
		return new VoucherProvider(voucherClientManager(), voucherInternalManager());
	}

	@Bean
	public VoucherRepository voucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		return new VoucherJdbcRepositoryImpl(jdbcTemplate, voucherProvider());
	}

	@Bean
	public CustomerRepository customerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		return new CustomerJdbcRepository(jdbcTemplate);
	}
}
