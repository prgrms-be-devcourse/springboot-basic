package com.programmers.order.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.programmers.order.factory.VoucherManagerFactory;
import com.programmers.order.io.Console;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.manager.FixVoucherManager;
import com.programmers.order.manager.PercentVoucherManager;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.repository.customer.CustomerJdbcRepository;
import com.programmers.order.repository.customer.CustomerRepository;
import com.programmers.order.repository.wallet.WalletRepository;
import com.programmers.order.repository.wallet.WalletJdbcRepository;
import com.programmers.order.repository.voucher.JdbcVoucherRepository;
import com.programmers.order.repository.voucher.VoucherRepository;
import com.zaxxer.hikari.HikariDataSource;

@ComponentScan(basePackages = "com.programmers.order")
@TestConfiguration
public class TestConfig {

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
	public NamedParameterJdbcTemplate jdbcTemplate(JdbcTemplate jdbcTemplate) {
		return new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public CustomerRepository customerRepository(DataSource dataSource, JdbcTemplate jdbcTemplate,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		return new CustomerJdbcRepository(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Bean
	public FixVoucherManager fixVoucherManager(Output output, Input input) {
		return new FixVoucherManager(input, output);
	}

	@Bean
	public PercentVoucherManager percentVoucherManager(Output output, Input input) {
		return new PercentVoucherManager(input, output);
	}

	@Bean
	public Output output() {
		return new Console();
	}

	@Bean
	public Input input() {
		return new Console();
	}

	@Bean
	public VoucherManagerFactory voucherManagerFactory(List<VoucherManager> voucherManagers) {
		return new VoucherManagerFactory(voucherManagers);
	}

	@Bean
	public VoucherRepository voucherRepository(VoucherManagerFactory voucherManagerFactory,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		return new JdbcVoucherRepository(voucherManagerFactory, namedParameterJdbcTemplate);
	}

	@Bean
	public WalletRepository customerVoucherRepository(DataSource dataSource, JdbcTemplate jdbcTemplate
			, NamedParameterJdbcTemplate namedParameterJdbcTemplate, VoucherManagerFactory voucherManagerFactory) {
		return new WalletJdbcRepository(dataSource, jdbcTemplate, namedParameterJdbcTemplate,
				voucherManagerFactory);
	}

}
