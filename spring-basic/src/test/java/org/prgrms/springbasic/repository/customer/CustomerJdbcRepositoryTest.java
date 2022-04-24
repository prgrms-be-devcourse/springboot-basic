package org.prgrms.springbasic.repository.customer;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.repository.voucher.VoucherJdbcRepository;
import org.prgrms.springbasic.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.NoSuchElementException;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.prgrms.springbasic.domain.customer.CustomerType.BLACK;
import static org.prgrms.springbasic.domain.customer.CustomerType.NORMAL;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.springbasic.repository"}
    )
    static class TestConfig {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("test1234")
                    .type(HikariDataSource.class)
                    .build();
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
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new CustomerJdbcRepository(jdbcTemplate);
        }

        @Bean
        public VoucherRepository voucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new VoucherJdbcRepository(jdbcTemplate);
        }
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    EmbeddedMysql embeddedMysql;

    Customer customer = Customer.normalCustomer(randomUUID(), "new-user");

    @BeforeAll
    void setUp() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql")).start();

        voucherRepository.deleteVouchers();
        customerRepository.deleteCustomers();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("고객을 저장하면 반환되는 객체는 저장한 고객과 같아야 하고 레포지토리의 사이즈는 1이 되어야 한다.")
    void testSave() {
        var newCustomer = customerRepository.save(customer);

        assertThat(newCustomer, notNullValue());
        assertThat(newCustomer, samePropertyValuesAs(customer));
        assertThat(customerRepository.countCustomers(), is(1));
    }

    @Test
    @Order(2)
    @DisplayName("중복되는 아이디를 가진 고객을 저장할 경우 예외가 발생한다.")
    void testDuplicatedCustomer() {
        assertThrows(DataAccessException.class, () -> customerRepository.save(customer));
    }

    @Test
    @Order(3)
    @DisplayName("고객 아이디로 고객을 조회할 수 있다.")
    void testFindByCustomerId() {
        var black = Customer.blackCustomer(randomUUID(), "new-black");
        customerRepository.save(black);

        var retrievedCustomer = customerRepository.findByCustomerId(black.getCustomerId());

        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(black));
    }

    @Test
    @Order(4)
    @DisplayName("특정 바우처를 보유한 고객을 조회할 수 있다.")
    void testFindByVoucherId() {
        var voucher = Voucher.fixedVoucher(randomUUID(), 10);
        voucherRepository.save(voucher);

        var retrievedCustomer = customerRepository.findByVoucherId(voucher.getVoucherId());

        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @Order(5)
    @DisplayName("고객 아이디로 고객을 찾지 못하는 경우 빈 객체가 반환되어야 한다.")
    void testFindByIdException() {
        var retrievedCustomer = customerRepository.findByCustomerId(randomUUID());

        assertThat(retrievedCustomer.isEmpty(), is(true));
        assertThrows(NoSuchElementException.class, retrievedCustomer::get);
    }

    @Test
    @Order(6)
    @DisplayName("모든 고객을 조회했을 때 저장한 고객 객체를 가지고 있어야 하며 사이즈가 같아야 한다.")
    void testFindAll() {
        customerRepository.deleteCustomers();

        Customer newCustomer1 = Customer.normalCustomer(randomUUID(), "new-user1");
        Customer newCustomer2 = Customer.normalCustomer(randomUUID(), "new-user2");
        Customer newCustomer3 = Customer.normalCustomer(randomUUID(), "new-user3");
        Customer newCustomer4 = Customer.normalCustomer(randomUUID(), "new-user4");
        Customer newCustomer5 = Customer.normalCustomer(randomUUID(), "new-user5");

        customerRepository.save(newCustomer1);
        customerRepository.save(newCustomer2);
        customerRepository.save(newCustomer3);
        customerRepository.save(newCustomer4);
        customerRepository.save(newCustomer5);

        var customers = customerRepository.findCustomers();

        assertThat(customers.size(), is(customerRepository.countCustomers()));
        assertThat(customers.get(0), samePropertyValuesAs(newCustomer1));
        assertThat(customers, containsInRelativeOrder(samePropertyValuesAs(newCustomer1), samePropertyValuesAs(newCustomer2), samePropertyValuesAs(newCustomer3), samePropertyValuesAs(newCustomer4), samePropertyValuesAs(newCustomer5)));
    }

    @Test
    @Order(7)
    @DisplayName("입력된 고객 정보대로 업데이트가 잘 되어야 한다.")
    void testUpdateCustomer() {
        var newCustomer = Customer.blackCustomer(randomUUID(), "new-black");
        var beforeModified = newCustomer.getModifiedAt();
        customerRepository.save(newCustomer);

        var updatedCustomer = newCustomer.update(NORMAL, "updated-black");
        var retrievedCustomer = customerRepository.update(updatedCustomer);

        assertThat(retrievedCustomer, notNullValue());
        assertThat(retrievedCustomer.getCustomerId(), is(newCustomer.getCustomerId()));
        assertThat(retrievedCustomer.getCustomerType(), not(is(BLACK)));
        assertThat(retrievedCustomer.getName(), is("updated-black"));
        assertThat(beforeModified, not(is(retrievedCustomer.getModifiedAt()))); //수정을 하면 수정된 시간이 변경되어야 한다.
    }

    @Test
    @Order(8)
    @DisplayName("고객 아이디로 고객을 삭제할 수 있다.")
    void testDeleteByCustomerId() {
        var customer = Customer.normalCustomer(randomUUID(), "new-user");
        var savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer, samePropertyValuesAs(customer));

        customerRepository.deleteByCustomerId(savedCustomer.getCustomerId());

        var deletedCustomer = customerRepository.findByCustomerId(savedCustomer.getCustomerId());

        assertThat(deletedCustomer.isEmpty(), is(true));
    }

    @Test
    @Order(9)
    @DisplayName("모든 고객을 삭제하고 나면 레객포지토리의 사이즈는 0이 되어야한다.")
    void testDeleteAll() {
        assertThat(customerRepository.countCustomers(), greaterThan(0));

        customerRepository.deleteCustomers();

        assertThat(customerRepository.findCustomers().isEmpty(), is(true));
        assertThat(customerRepository.countCustomers(), is(0));
    }

}