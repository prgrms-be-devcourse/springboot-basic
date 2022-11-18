package org.prgrms.kdt.customer;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerJdbcStorageTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.customer"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    CustomerJdbcStorage customerJdbcStorage;

    @Autowired
    DataSource dataSource;


    Customer customer;
    String customerId;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        customerId = UUID.randomUUID().toString();
        customer = new Customer(customerId, "user1", "user1@gmail.com");
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("voucher_mgmt", classPathScript("customer.sql"))
                .start();

        customerJdbcStorage.insert(customer);
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("고객을 새로 추가할 수 있다.")
    void test1() {
        // given
        String newCustomerId = UUID.randomUUID().toString();
        Customer newCustomer = new Customer(newCustomerId, "new-user", "new-user@gmail.com");

        // when
        customerJdbcStorage.insert(newCustomer);

        customerJdbcStorage.findById(newCustomerId)
                .ifPresent(findCustomer ->
                        assertThat(newCustomer).usingRecursiveComparison()
                                .isEqualTo(findCustomer));
    }

    @Test
    @DisplayName("고객의 아이디를 통해 특정 고객을 조회할 수 있다.")
    void test2() {
        // given
        String customerId = customer.getCustomerId();

        // then
        customerJdbcStorage.findById(customerId)
                .ifPresent(findCustomer ->
                        assertThat(customer).usingRecursiveComparison()
                                .isEqualTo(findCustomer));

    }

    @Test
    @DisplayName("모든 고객을 조회할 수 있다.")
    void test3() {
        // when
        List<Customer> allCustomer = customerJdbcStorage.findAll();

        // then
        assertFalse(allCustomer.isEmpty());
    }

    @Test
    @DisplayName("고객 아이디를 활용하여 특정 고객을 저장소에서 삭제할 수 있다.")
    void tes4t() {
        // given
        String id = UUID.randomUUID().toString();
        Customer deleteCustomer = new Customer(id, "delete-user", "delete-user@gmail.com");

        customerJdbcStorage.insert(deleteCustomer);

        // when
        customerJdbcStorage.deleteCustomerById(deleteCustomer.getCustomerId());

        // then
        assertEquals(Optional.empty(),
                customerJdbcStorage.findById(deleteCustomer.getCustomerId()));
    }

    @Test
    @DisplayName("고객의 이름을 수정할 수 있다.")
    void test6() {
        // given
        String name = "new-name";
        customer.changeName(name);

        // when
        customerJdbcStorage.update(customer);

        // then
        customerJdbcStorage.findById(customer.getCustomerId())
                .ifPresent(getCustomer -> assertThat(customer).usingRecursiveComparison()
                        .isEqualTo(getCustomer));
    }
}
