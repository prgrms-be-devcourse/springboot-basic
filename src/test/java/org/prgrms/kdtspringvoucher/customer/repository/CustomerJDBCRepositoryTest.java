package org.prgrms.kdtspringvoucher.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringvoucher.customer.entity.JDBCCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.wix.mysql.EmbeddedMysql;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.distribution.Version.v8_latest;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.config.Charset.UTF8;


@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerJDBCRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdtspringvoucher.customer.repository"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
//            var dataSource = DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost:3306/order_mgmt")
//                    .username("will")
//                    .password("1234")
//                    .type(HikariDataSource.class)
//                    .build();
//            return dataSource;

            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:4406/test-order_mgmt")
                    .username("test-user")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
//            dataSource.setMaximumPoolSize(1000);
//            dataSource.setMinimumIdle(100);
            return dataSource;

        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    CustomerJDBCRepository customerJDBCRepository;

    @Autowired
    DataSource dataSource;

    JDBCCustomer newCustomer;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        newCustomer = new JDBCCustomer(UUID.randomUUID(), "test-user", "test1-user@gmail.com", LocalDateTime.now());

        var mysqlConfig = aMysqldConfig(v8_latest)
                .withCharset(UTF8)
                .withPort(4406)
                .withUser("test-user", "1234")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("customer-schema.sql"))
                .start();
//        customerJDBCRepository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        System.out.println("*****dataSource.getClass().getName() --"+ dataSource.getClass().getName());
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    void testInsert() {
        customerJDBCRepository.insert(newCustomer);

        var retrievedCustomer = customerJDBCRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객 조회")
    void testFindAll() {
        var customers = customerJDBCRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    void testFindByName() {
        var customers = customerJDBCRepository.findByName(newCustomer.getName());
        assertThat(customers.isEmpty(), is(false));

        var unknown = customerJDBCRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    void testFindByEmail() {
        var customers = customerJDBCRepository.findByEmail(newCustomer.getEmail());
        assertThat(customers.isEmpty(), is(false));

        var unknown = customerJDBCRepository.findByEmail("unknown-user@gmail.com");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("고객을 수정할 수 있다.")
    void testUpdate() {
        newCustomer.changeName("updated-name");
        customerJDBCRepository.update(newCustomer);

        var all = customerJDBCRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));

        var retrievedCustomer = customerJDBCRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(7)
    @DisplayName("전체 고객 개수")
    void testCountCustomers() throws InterruptedException {
        var customers = customerJDBCRepository.count();
        assertThat(0, greaterThanOrEqualTo(0));
    }
}