package org.prgrms.voucherapplication.customer;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.voucherapplication"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
//            EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
//                    .generateUniqueName(true)
//                    .setType(H2)
//                    .setScriptEncoding("UTF-8")
//                    .ignoreFailedDrops(true)
//                    .addScript("schema.sql")
//                    .build();

            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_application")
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

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now());
        MysqldConfig mysqldConfig = MysqldConfig.aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)     // 임의의 포트 번호 지정
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("voucher_application", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    void testInsert() {
        try {
            customerJdbcRepository.insert(newCustomer);
        } catch (BadSqlGrammarException e) {
            logger.error("Got BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode(), e);
        }

        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        MatcherAssert.assertThat(retrievedCustomer.isEmpty(), is(false));
        MatcherAssert.assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다.")
    void testFindAll() {

        List<Customer> customers = customerJdbcRepository.findAll();
        assertThat(customers).isNotEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    void testFindByName() {
        Optional<Customer> customer = customerJdbcRepository.findByName(newCustomer.getName());
        assertThat(customer).isPresent();

        Optional<Customer> unknown = customerJdbcRepository.findByEmail("unkonwn-user");
        assertThat(unknown).isEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    void testFindByEmail() {

    }

    @Test
    @Order(6)
    @DisplayName("고객을 수정할 수 있다.")
    void testUpdate() {
        newCustomer.changeName("updated-user");
        customerJdbcRepository.update(newCustomer);

        List<Customer> all = customerJdbcRepository.findAll();
        MatcherAssert.assertThat(all, hasSize(1));
        MatcherAssert.assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));

        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        MatcherAssert.assertThat(retrievedCustomer.isEmpty(), is(false));
        MatcherAssert.assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }
}
