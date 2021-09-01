package org.prgrms.kdt.jdbcRepository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.domain","org.prgrms.kdt.jdbcRepository"}
    )
    static class config{
        @Bean
        public DataSource dataSource(){
            var datasource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order-mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class) //쓸 구현체를 넣어준다 .
                    .build();
            datasource.setMaximumPoolSize(100);
            datasource.setMinimumIdle(100);
            return datasource;
        }
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;

    CustomerEntity testCustomer,testCustomer2;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp(){
        testCustomer = new CustomerEntity(UUID.randomUUID(), "test-name", "test-user@naver.com", LocalDateTime.now());
        testCustomer2 = new CustomerEntity(UUID.randomUUID(), "test-name2", "test-user@naver.com2", LocalDateTime.now());
        var mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia.Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order-mgmt", classPathScript("schema.sql"))
                .start();
    }

    @Test
    @Order(1)
    @DisplayName("고객을 추가할 수 있다.")
    void insert() {
        customerRepository.insert(testCustomer);

        System.out.println("customerID -> "+testCustomer.getCustomerId());
        var retrievedCustomer = customerRepository.findById(testCustomer.getCustomerId());
        System.out.println(retrievedCustomer.get());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(testCustomer));
    }


    @Test
    @Order(2)
    @DisplayName("고객 아이디로 조회할 수 있다.")
    void findById() {
        var findCustomer = customerRepository.findById(testCustomer.getCustomerId());
        assertThat(findCustomer.get(), samePropertyValuesAs(testCustomer));
    }

    @Test
    @Order(3)
    @DisplayName("고객의 이메일로 조회할 수 있다.")
    void findByEmail() {
        var findCustomer = customerRepository.findByEmail(testCustomer.getEmail());
        assertThat(findCustomer.get(), samePropertyValuesAs(testCustomer));
    }


    @Test
    @Order(4)
    @DisplayName("고객의 정보를 수정 할 수 있다.")
    void update() {
//        customerJdbcRepository.insert(testCustomer);
        var changeCustomer = CustomerEntity.builder()
                .customerId(testCustomer.getCustomerId())
                .name("changeName")
                .email("change@naver.com")
                .createdAt(testCustomer.getCreatedAt())
                .build();
        customerRepository.update(changeCustomer);
        var retrivedCustomer = customerRepository.findById(changeCustomer.getCustomerId());
        assertThat(retrivedCustomer.isEmpty(),is(false));
        assertThat(retrivedCustomer.get(), samePropertyValuesAs(changeCustomer));
    }

    @Test
    @Order(5)
    @DisplayName("저장된 고객 정보 일괄 삭제한다.")
    void deleteAll() {
        customerRepository.deleteAll();
        var customerList = customerRepository.findAll();
        assertThat(customerList.isEmpty(), is(true));
        assertThat(customerList.size(), is(0));
    }

    @Test
    @Order(6)
    @DisplayName("저장된 고객의 정보를 모두 가져온다.")
    void findAll() {
        customerRepository.insert(testCustomer);
        customerRepository.insert(testCustomer2);

        List<CustomerEntity> customerList = customerRepository.findAll();
        assertThat(customerList.isEmpty(), is(false));
        assertThat(customerList.size(), is(2));
    }

    @Test
    @Order(7)
    @DisplayName("특정 고객의 정보를 삭제한다.")
    void deleteById() {
        customerRepository.deleteById(testCustomer.getCustomerId());
        var deleteCustomer = customerRepository.findById(testCustomer.getCustomerId());
        assertThat(deleteCustomer.isEmpty(), is(true));
    }
}