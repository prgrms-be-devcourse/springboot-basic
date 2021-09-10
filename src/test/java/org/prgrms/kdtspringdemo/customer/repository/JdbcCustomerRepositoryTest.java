package org.prgrms.kdtspringdemo.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcCustomerRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepositoryTest.class);
    @Autowired
    DataSource dataSource;
    @Configuration
    @ComponentScan(basePackages = {
            "org.prgrms.kdtspringdemo.customer",
    })
    static class Config {
        @Bean
        public DataSource dataSource() {
//            Embedded DataBase로 설정하기(mysql v5.7.latest)
//            var dataSource = DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost:2215/customer_mgmt")
//                    .username("test")
//                    .password("test1234!")
//                    .build();
//            return dataSource;

//          docker에 올린 DB로 시작하기(mysql v8.0.11)
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/customer_mgmt")
                    .username("root")
                    .password("root1234!")
                    .build();
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    Customer newCustomer;

    EmbeddedMysql embeddMysql;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test1-user@gmail.com", "Normal", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
// Embedded DataBase로 실행하기(mysql v5.7.latest)
//        var mysqlConfig = aMysqldConfig(v5_7_latest)
//                .withCharset(UTF8)
//                .withPort(2215)
//                .withUser("test", "test1234!")
//                .build();

//        embeddMysql = anEmbeddedMysql(mysqlConfig)
//                .addSchema("customer_mgmt", classPathScript("schema.sql"))
//                .start();

        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() throws InterruptedException {
        try {
            jdbcCustomerRepository.insert(newCustomer);
        } catch (BadSqlGrammarException e) {
            logger.error("Get BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode(), e);
        }
//        customerJdbcRepository.insert(newCustomer);

        var retrievedCustomer = jdbcCustomerRepository.findById(newCustomer.getId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs((Customer) newCustomer));
    }
    @Test
    @Order(2)
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() throws InterruptedException {
        var customers = jdbcCustomerRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }
    @Test
    @Order(3)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindName() throws InterruptedException {
        var customers = jdbcCustomerRepository.findByName(newCustomer.getName());
        assertThat(customers.isEmpty(), is(false));
        var unknown = jdbcCustomerRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }
    @Test
    @Order(4)
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    public void testFindEmail() throws InterruptedException {
        var customers = jdbcCustomerRepository.findByEmail(newCustomer.getEmail());
        assertThat(customers.isEmpty(), is(false));
        var unknown = jdbcCustomerRepository.findByEmail("unknown-user@gmail.com");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("타입으로 고객을 조회할 수 있다.")
    public void testFindType() throws InterruptedException {
        var customers = jdbcCustomerRepository.findByType(newCustomer.getType());
        assertThat(customers.isEmpty(), is(false));
        var unknown = jdbcCustomerRepository.findByType("unknown-type");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() throws InterruptedException {
        newCustomer.changeName("updated-user");
        jdbcCustomerRepository.update(newCustomer);
        var all = jdbcCustomerRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));
        var retrievedCustomer = jdbcCustomerRepository.findById(newCustomer.getId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }
}