package org.prgrms.kdt.model.repository.jdbc;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.ScriptResolver.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgrms.kdt.model.entity.CustomerEntity;
import org.prgrms.kdt.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {
	@Configuration
	@ComponentScan(
		basePackages = {"org.prgrms.kdt.model.repository.jdbc"}
	)
	static class Config {

		@Bean
		public DataSource dataSource() {
			HikariDataSource dataSource = DataSourceBuilder.create()
				.url("jdbc:mysql://localhost:2215/test-order-mgmt")
				.username("test")
				.password("test1234!")
				.type(HikariDataSource.class)
				.build();

			dataSource.setMinimumIdle(10);
			dataSource.setMaximumPoolSize(100);
			return dataSource;
		}

		@Bean
		public JdbcTemplate jdbcTemplate(DataSource dataSource) {
			return new JdbcTemplate(dataSource);
		}

		@Bean
		public IdGenerator idGenerator() {
			return new IdGenerator();
		}
	}

	@Autowired
	CustomerJdbcRepository customerJdbcRepository;

	@Autowired
	IdGenerator idGenerator;

	private CustomerEntity newCustomer;

	EmbeddedMysql embeddedMysql;

	@BeforeAll
	void setup() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
		String formattedDateTime = now.format(formatter);
		LocalDateTime now6Digit = LocalDateTime.parse(formattedDateTime, formatter);

		newCustomer = new CustomerEntity(idGenerator.getRandomId(), "test-user", "test-user@gmail.com", now6Digit);
		var mysqlConfig = aMysqldConfig(v5_7_27)
			.withCharset(Charset.aCharset("utf8mb4", "utf8mb4_bin"))
			.withPort(2215)
			.withUser("test", "test1234!")
			.withTimeZone("Asian/Seoul")
			.build();

		embeddedMysql = anEmbeddedMysql(mysqlConfig)
			.addSchema("test-order-mgmt", classPathScript("customerSchema.sql"))
			.start();
	}

	@AfterAll
	void cleanup() {
		embeddedMysql.stop();
	}

	@Test
	@Order(1)
	@DisplayName("고객을 추가할 수 있다.")
	public void testInsert() {
		customerJdbcRepository.create(newCustomer);

		var retrievedCustomer = customerJdbcRepository.findById(newCustomer.customerId());

		assertThat(retrievedCustomer.isEmpty(), is(false));
		assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
	}

	@Test
	@Order(2)
	@DisplayName("전체 고객을 조회할 수 있다.")
	public void testFindAll() {
		List<CustomerEntity> customers = customerJdbcRepository.findAll();
		assertThat(customers.isEmpty(), is(false));
	}

	@Test
	@Order(3)
	@DisplayName("고객을 수정할 수 있다.")
	public void updateCustomer() {
		CustomerEntity updatedCustomer = new CustomerEntity(newCustomer.customerId(), newCustomer.name(),
			newCustomer.email(), newCustomer.createdAt());

		customerJdbcRepository.update(updatedCustomer);

		List<CustomerEntity> all = customerJdbcRepository.findAll();
		assertThat(all, hasSize(1));
		assertThat(all, everyItem(samePropertyValuesAs(updatedCustomer)));

		Optional<CustomerEntity> retrievedCustomer = customerJdbcRepository.findById(newCustomer.customerId());
		assertThat(retrievedCustomer.isEmpty(), is(false));
		assertThat(retrievedCustomer.get(), samePropertyValuesAs(updatedCustomer));
	}
}