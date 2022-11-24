package org.programmers.springbootbasic.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.springbootbasic.domain.customer.model.Customer;
import org.programmers.springbootbasic.domain.customer.dto.CustomerInsertDto;
import org.programmers.springbootbasic.domain.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.programmers.springbootbasic.domain.customer.repository"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {

            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/spring_basic")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    private CustomerRepository jdbcCustomerRepository;

    @Autowired
    private DataSource dataSource;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        this.embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("spring_basic", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @DisplayName("Customer가 주어졌을 때 삽입한 결과가 그 전 조회 시의 row 수보다 1 증가해야 한다.")
    public void Customer_삽입_테스트() throws Exception {
        //given
        String name = "김영빈";
        String email = "a@gmail.com";
        CustomerInsertDto customerInputDto = new CustomerInsertDto(name, email);

        List<Customer> retrievedAllFirst = jdbcCustomerRepository.findAll();
        int beforeSize = retrievedAllFirst.size();

        // when
        jdbcCustomerRepository.save(customerInputDto);

        //then
        List<Customer> retrievedAllLast = jdbcCustomerRepository.findAll();
        int afterSize = retrievedAllLast.size();
        assertThat(afterSize, is(beforeSize + 1));
    }



}