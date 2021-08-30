package org.prgrms.kdt.devcourse.customer;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.devcourse.customer"})
    static class config{
        @Bean
        public DataSource dataSource(){
            //using only test
            DataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
        @Bean
        public JdbcCustomerRepository jdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate){
            return new JdbcCustomerRepository(jdbcTemplate);
        }


    }

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;
    @Autowired
    DataSource dataSource;

    Customer newCustomer ;

    EmbeddedMysql embeddedMysql;



    @BeforeAll
    void setUp(){
        newCustomer = new Customer(UUID.randomUUID(),"test-user","test-user@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        var  mysqldConfig= aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test_mgmt",classPathScript("schema.sql"))
                .start();
    }


    @AfterAll
    void cleanUp(){

        embeddedMysql.stop();
    }

    @Test
    @DisplayName("새로운 고객을 추가할 수 있다.")
    @Order(1)
    void testInsert() {
        jdbcCustomerRepository.deleteAll();
        var customers = jdbcCustomerRepository.insert(newCustomer);

        var retrievedCustomer = jdbcCustomerRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(),is(false));
        assertThat(retrievedCustomer.get(),samePropertyValuesAs(newCustomer));

    }



    @Test
    @DisplayName("모든 고객 조회할 수 있다.")
    @Order(2)
    void testFindAllCustomer() {
        List<Customer> customers = jdbcCustomerRepository.findAll();
        assertThat(customers.isEmpty(),is(false));
    }

    @Test
    @Order(3)
    @DisplayName("고객 ID로 고객을 찾을 수 있다.")
    void testFindById() {
        var foundCustomer = jdbcCustomerRepository.findById(newCustomer.getCustomerId());
        assertThat(foundCustomer.isPresent(),is(true));
        assertThat(foundCustomer.get(),samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(4)
    @DisplayName("이름으로 고객을 찾을 수 있다.")
    void testFindByName() {
        var foundCustomer = jdbcCustomerRepository.findByName(newCustomer.getName());
        assertThat(foundCustomer.isPresent(),is(true));
        assertThat(foundCustomer.get(),samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(5)
    @DisplayName("이메일로 고객을 찾을 수 있다.")
    void testFindByIEmail() {
        var foundCustomer = jdbcCustomerRepository.findByIEmail(newCustomer.getEmail());
        assertThat(foundCustomer.isPresent(),is(true));
        assertThat(foundCustomer.get(),samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(6)
    @DisplayName("고객 정보(이름)를 변경할 수 있다.")
    void testUpdateCustomer() {
        newCustomer.changeName("update-user");
        var updatedCustomers = jdbcCustomerRepository.update(newCustomer);

        var updatedCustomer = jdbcCustomerRepository.findByName(newCustomer.getName());
        assertThat(updatedCustomer.isPresent(),is(true));
        assertThat(updatedCustomer.get(),samePropertyValuesAs(newCustomer));

    }
    @Test
    @Order(7)
    @DisplayName("고객 전부를 삭제할 수 있다.")
    void testDeleteAll() {
        jdbcCustomerRepository.deleteAll();
        List<Customer> customers = jdbcCustomerRepository.findAll();
        assertThat(customers.isEmpty(),is(true));
    }
}