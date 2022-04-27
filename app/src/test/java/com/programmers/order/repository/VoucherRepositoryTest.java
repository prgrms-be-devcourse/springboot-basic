package com.programmers.order.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.ScriptResolver.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.programmers.order.domain.FixedAmountVoucher;
import com.programmers.order.domain.PercentDiscountVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.factory.VoucherManagerFactory;
import com.programmers.order.io.Console;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.manager.FixVoucherManager;
import com.programmers.order.manager.PercentVoucherManager;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.repository.voucher.JdbcVoucherRepository;
import com.programmers.order.repository.voucher.VoucherRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VoucherRepositoryTest {

	@Configuration
	@ComponentScan(basePackages = {"com.programmers.order"})
	static class Config {

		@Bean
		public DataSource dataSource() {
			HikariDataSource dataSource = DataSourceBuilder
					.create()
					.url("jdbc:mysql://localhost:2215/kdt_order")
					.username("test")
					.password("test")
					.type(HikariDataSource.class)
					.build();

			dataSource.setMaximumPoolSize(1000);
			dataSource.setMinimumIdle(100);

			return dataSource;
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

	}

	@Autowired
	private ApplicationContext applicationContext;

	private EmbeddedMysql embeddedMysql;

	@Autowired
	private VoucherRepository voucherRepository;

	@DisplayName("최초 딱 한번 실행 메소드")
	@BeforeAll
	void init() {

		MysqldConfig mysqldConfig = aMysqldConfig(v8_0_11)
				.withCharset(Charset.UTF8)
				.withPort(2215)
				.withUser("test", "test")
				.withTimeZone("Asia/Seoul")
				.build();

		embeddedMysql = anEmbeddedMysql(mysqldConfig)
				.addSchema("kdt_order", classPathScript("schema.sql"))
				.start();
	}

	@AfterAll
	public void embededDataBaseStop() {
		embeddedMysql.stop();
	}

	@Order(1)
	@Test
	@DisplayName("context test")
	void testContext() {
		//given
		//when
		//then
		Assertions.assertNotNull(applicationContext);
	}

	@Order(2)
	@Test
	@DisplayName("insert")
	void testInsert() {
		//given
		List<Voucher> vouchers = getVouchers();
		//when
		vouchers.stream()
				.forEach(value -> {
					//then
					Voucher voucher = voucherRepository.saveVoucher(value);
					MatcherAssert.assertThat(value, Matchers.samePropertyValuesAs(voucher));
				});

	}

	@Order(3)
	@Test
	@DisplayName("look up vouchers")
	void lookUpTest() {
		//given
		List<Voucher> vouchers = getVouchers();

		//when
		AtomicInteger index = new AtomicInteger();
		List<Voucher> insertedVouchers = voucherRepository.getVouchers();

		//then
		MatcherAssert.assertThat(vouchers.size(), Matchers.is(2));

	}

	@Test
	@DisplayName("voucher id 조회")
	void testFindById() {
		//given
		//when

		//then
	}

	public List<Voucher> getVouchers() {
		return List.of(FixedAmountVoucher.create(1000), PercentDiscountVoucher.create(10));
	}

}
