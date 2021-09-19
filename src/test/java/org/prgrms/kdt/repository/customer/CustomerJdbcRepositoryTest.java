package org.prgrms.kdt.repository.customer;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.customer.repository.CustomerJdbcRepository;
import org.prgrms.kdt.customer.model.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    private EmbeddedMysql embeddedMysql;
    private Customer newCustomer;


    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test-command_application")
                .username("test").password("test1234!").type(HikariDataSource.class).build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

    }

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;


    @Autowired
    DataSource dataSource;

    @BeforeAll
    void setUp() {
        MysqldConfig config = aMysqldConfig(v8_0_11)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .withTimeout(2, TimeUnit.MINUTES)
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-command_application", classPathScript("schema.sql"))
            .start();

        newCustomer = new Customer(
            UUID.randomUUID(),
            "test-user",
            "test-user@gmail.com",
            LocalDateTime.now());

    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }


    @Test
    @Order(1)
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    @Order(2)
    void testInsert() {
        customerJdbcRepository.insert(newCustomer);
        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    @Order(3)
    void testFindAll() {
        var customers = customerJdbcRepository.findAllCustomer();
        assertThat(customers.isEmpty(), is(false));
    }


    @Test
    @DisplayName("아이디로 고객을 조회할 수 있다.")
    @Order(4)
    void testFindById() {
        var customer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(customer.isEmpty(), is(false));

        var unknown = customerJdbcRepository.findById(UUID.randomUUID());
        assertThat(unknown.isEmpty(), is(true));
    }



    @Test
    @DisplayName("고객의 타입을 수정할 수 있다.")
    @Order(5)
    void testUpdate() {
        newCustomer.changeCustomerType(CustomerType.BLACK);
        customerJdbcRepository.updateType(newCustomer);

        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId()).get();
        assertThat(retrievedCustomer.getCustomerType(), is(CustomerType.BLACK));
        assertThat(retrievedCustomer, samePropertyValuesAs(newCustomer));

    }

    @Test
    @DisplayName("고객 타입으로 고객들을 조회할 수 있다.")
    @Order(6)
    void testFindByType() {
        var blackList = customerJdbcRepository.findByCustomerType(CustomerType.BLACK);
        assertThat(blackList, hasSize(1));
        assertThat(blackList, everyItem(samePropertyValuesAs(newCustomer)));
    }

}
