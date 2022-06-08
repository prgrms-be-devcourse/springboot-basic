package org.programmers.kdt.weekly.customer.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.programmers.kdt.weekly.customer.model.Customer;
import org.programmers.kdt.weekly.customer.model.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

	private static EmbeddedMysql embeddedMysql;

	@BeforeAll
	static void setup() {
		var config = aMysqldConfig(v5_7_latest)
			.withCharset(Charset.UTF8)
			.withPort(2215)
			.withUser("test", "test1234!")
			.withTimeZone("Asia/Seoul")
			.build();
		embeddedMysql = anEmbeddedMysql(config)
			.addSchema("test-voucher_mgmt", ScriptResolver.classPathScript("schema.sql"))
			.start();
	}

	@AfterAll
	static void cleanup() {
		embeddedMysql.stop();
	}

	@Autowired
	CustomerRepository jdbcCustomerRepository;

	Customer normalCustomer = new Customer(UUID.randomUUID(), "hyebpark@gmail.com", "hyeb",
		CustomerType.NORMAL);
	Customer blackCustomer = new Customer(UUID.randomUUID(), "black@gmail.com", "black",
		CustomerType.BLACK);

	@BeforeEach
	void setOn() {
		jdbcCustomerRepository.deleteAll();
		jdbcCustomerRepository.insert(normalCustomer);
		jdbcCustomerRepository.insert(blackCustomer);
	}

	@Test
	@DisplayName("id가 중복된 customer 저장시 DuplicateKeyException 발생 되어야함")
	void insert() {
		assertThatThrownBy(() -> jdbcCustomerRepository.insert(normalCustomer)).isInstanceOf(
			DuplicateKeyException.class);
	}

	@Test
	@DisplayName("모든 고객이 담긴 list를 return 해야함")
	void findByAll() {
		List<Customer> customers = jdbcCustomerRepository.findAll();
		assertThat(customers.size(), is(2));
	}

	@Test
	@DisplayName("고객 type이 Black으로 수정되어야함")
	void update() {
		//given
		normalCustomer.changeCustomerType(CustomerType.BLACK);
		//when
		Customer updateCustomer = jdbcCustomerRepository.update(normalCustomer);
		//then
		assertThat(normalCustomer, samePropertyValuesAs(updateCustomer));
	}

	@Test
	@DisplayName("모든 고객을 삭제해서 findAll이 emptyList를 반환해야함")
	void deleteAll() {
		//given
		//when
		jdbcCustomerRepository.deleteAll();
		//then
		assertThat(jdbcCustomerRepository.findAll(), is(Collections.emptyList()));
	}

	@Test
	@DisplayName("해당 email을 가지는 고객을 return 해야함")
	void findByEmail() {
		var findCustomer = jdbcCustomerRepository.findByEmail(normalCustomer.getEmail());
		assertThat(findCustomer.isPresent(), is(true));
		assertThat(normalCustomer, samePropertyValuesAs(findCustomer.get()));

	}

	@Test
	@DisplayName("NORMAL 타입 고객 리스트를 return 해야함")
	void findByTypeNormal() {
		var findCustomers = jdbcCustomerRepository.findByType(CustomerType.NORMAL.toString());
		assertThat(findCustomers.size(), is(1));
	}
}
