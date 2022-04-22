package org.prgrms.kdtspringdemo.domain.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static com.wix.mysql.config.Charset.UTF8;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = CustomerJdbcRepositoryTest.class)
class CustomerJdbcRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepositoryTest.class);

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdtspringdemo.domain.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
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

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now());
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
//    customerJdbcRepository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @Order(1)
    @DisplayName("고객을 저장하면 반환되는 객체는 저장한 고객과 같아야 하고 레포지토리의 사이즈는 1이 되어야 한다.")
    public void testSave() {
        Customer insertedCustomer = customerRepository.insert(newCustomer);

        assertThat(insertedCustomer, notNullValue());
        assertThat(insertedCustomer, samePropertyValuesAs(newCustomer));
        assertThat(customerRepository.count(), is(1));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 새로운 고객으로 업데이트 한다.")
    public void updateTest () throws Exception{
        // given
        Customer updatedCustomer = new Customer(UUID.randomUUID(), "update-user", "update-user@gmail.com", LocalDateTime.now());

        // when
        Customer update = customerRepository.update(updatedCustomer);

        // then
        assertThat(updatedCustomer, samePropertyValuesAs(update));
    }
    
    @Test
    @Order(2)
    @DisplayName("repository count 를 한다.")
    public void countTest () throws Exception{
        // given
        int count = customerRepository.count();
        
        // when

        // then
        assertThat(count, is(1));
    }
    
    @Test
    @Order(3)
    @DisplayName("모든 customer 를 찾아온다.")
    public void findAllTest () throws Exception{
        // given
        Customer secondCustomer = new Customer(UUID.randomUUID(), "second-user", "second-user@gmail.com", LocalDateTime.now());
        customerRepository.insert(secondCustomer);

        // when
        List<Customer> all = customerRepository.findAll();
        
        // then
        assertThat(all.size(), is(customerRepository.count()));
        assertThat(all.get(1), samePropertyValuesAs(secondCustomer));
    }

    @Test
    @Order(4)
    @DisplayName("id 를 통해 customer 를 찾아온다.")
    public void findByIdTest () throws Exception{
        // given
        UUID id = UUID.randomUUID();
        Customer idCustomer = new Customer(id, "id-user", "id-user@gmail.com", LocalDateTime.now());

        // when
        customerRepository.insert(idCustomer);

        // then
        assertThat(idCustomer, samePropertyValuesAs(customerRepository.findById(id)));
    }

    @Test
    @Order(5)
    @DisplayName("name 를 통해 customer 를 찾아온다.")
    public void findByName () throws Exception{
        // given
        String name = "name-user";
        Customer nameCustomer = new Customer(UUID.randomUUID(), name, "id-user@gmail.com", LocalDateTime.now());

        // when
        customerRepository.insert(nameCustomer);

        // then
        assertThat(nameCustomer, samePropertyValuesAs(customerRepository.findByName(name)));
    }

    @Test
    @Order(6)
    @DisplayName("email 를 통해 customer 를 찾아온다.")
    public void findByEmail () throws Exception{
        // given
        String email = "email-user@gamail.com";
        Customer emailCustomer = new Customer(UUID.randomUUID(), "email-name", email, LocalDateTime.now());

        // when
        customerRepository.insert(emailCustomer);

        // then
        assertThat(emailCustomer, samePropertyValuesAs(customerRepository.findByEmail(email)));
    }

    @Test
    @Order(7)
    @DisplayName("id 를 통해 customer 를 찾아온다.")
    public void DeleteAll () throws Exception{
        // given

        // when
        customerRepository.deleteAll();

        // then
        assertThat(customerRepository.count(), is(0));
    }

}