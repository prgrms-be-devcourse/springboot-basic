package org.prgrms.dev.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.dev.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @BeforeAll
    static void setup() {
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test_springboot_order", classPathScript("schema.sql"), classPathScript("data.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @DisplayName("고객을 추가할 수 있다.")
    @Test
    void createTest() {
        Customer customer = new Customer(UUID.randomUUID(), "tester03", "test03@gmail.com", LocalDateTime.now());

        jdbcCustomerRepository.create(customer);

        Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer.orElse(null)).isNotNull();
        assertThat(retrievedCustomer.get().getName()).isEqualTo(customer.getName());

    }

    @DisplayName("중복된 이메일로 고객을 추가할 수 없다.")
    @Test
    void createByDuplicateEmailTest() {
        Customer customer = new Customer(UUID.randomUUID(), "test01", "test01@gmail.com", LocalDateTime.now());

        assertThatThrownBy(() -> jdbcCustomerRepository.create(customer))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("This email already exists");
    }

    @DisplayName("이메일로 고객 정보를 조회할 수 있다.")
    @Test
    void findByEmailTest() {
        String email = "test02@gmail.com";

        Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findByEmail(email);

        assertThat(retrievedCustomer.orElse(null)).isNotNull();
    }

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.dev.customer"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_springboot_order")
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

}
