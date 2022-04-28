package com.voucher.vouchermanagement.repository.customer;

import com.voucher.vouchermanagement.configuration.YamlPropertiesFactory;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;

public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private static EmbeddedMysql embeddedMysql;

    @Configuration
    @ComponentScan(basePackages = {"com.voucher.vouchermanagement.repository.customer"})
    @PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
    @AutoConfigureJdbc
    static class AppConfig {
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();

        }

    }

    @BeforeAll
    public void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-voucher", classPathScript("schema.sql"))
                .start();
    }

    @AfterEach
    public void cleanEach() {
        customerRepository.deleteAll();
    }

    @AfterAll
    public void stopDb() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("Customer 데이터를 삽입한다.")
    public void insertTest() {

    }

    @Test
    @DisplayName("Id로 Customer를 식별한다.")
    public void findByIdTest() {

    }

    @Test
    @DisplayName("Email로 Customer를 식별한다.")
    public void findByEmailTest() {

    }

    @Test
    @DisplayName("Name으로 Customer를 식별한다.")
    public void findByNameTest() {

    }

    @Test
    @DisplayName("Customer 정보를 수정한다.")
    public void updateTest() {

    }

    @Test
    @DisplayName("다수의 Customer를 조회한다.")
    public void findAllTest() {

    }

    @Test
    @DisplayName("모든 Customer를 제거한다.")
    public void deleteAll() {

    }
}
