package org.prgrms.kdt.domain.customer;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringJUnitConfig
@PropertySource("classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerJdbcRepositoryTest {

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;
    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        newCustomer = new Customer(UUID.randomUUID(), "테스트", "test@gmail.com", LocalDateTime.now());
//        var mysqlConfig = aMysqldConfig(v8_0_11)
//                .withCharset(UTF8)
//                .withPort(2215)
//                .withUser("test", "test1234!")
//                .withTimeZone("Asia/Seoul")
//                .build();
//        anEmbeddedMysql(mysqlConfig)
//                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
//                .start();
        customerJdbcRepository.deleteAll();
    }

//    @AfterAll
//    void cleanUp() {
//        embeddedMysql.stop();
//    }

    @Test
    @Order(1)
//    @Disabled
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() {
        log.debug("[*] createdAt: {}", newCustomer.getCreatedAt());
        customerJdbcRepository.insert(newCustomer);

        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        log.debug("[*] retrievedCustomer: {}", retrievedCustomer);
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() throws InterruptedException {
        var customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("고객을 아이디로 조회할 수 있다.")
    public void testFindById() throws InterruptedException {
        var customer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(customer.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindByName() {
        var customer = customerJdbcRepository.findByName(newCustomer.getName());
        assertThat(customer.isEmpty(), is(false));
        var unknown = customerJdbcRepository.findByName(Name.valueOf("unknown"));
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    public void testFindByEmail() {
        var customer = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        assertThat(customer.isEmpty(), is(false));
        var unknown = customerJdbcRepository.findByEmail(Email.valueOf("unknown-user@gmail.com"));
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(7)
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() {
        newCustomer.getName().changeName("updated-name");
        customerJdbcRepository.update(newCustomer);
        var all = customerJdbcRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));

        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }


    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.domain.customer"}
    )
    static class Config {

        @Value("${application.username}")
        private String user;

        @Value("${application.password}")
        private String password;

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("kdt")
                    .password("kdt")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
}
