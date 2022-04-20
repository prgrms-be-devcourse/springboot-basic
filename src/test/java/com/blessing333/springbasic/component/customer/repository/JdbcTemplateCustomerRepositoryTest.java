package com.blessing333.springbasic.component.customer.repository;

import com.blessing333.springbasic.component.customer.CustomerFactory;
import com.blessing333.springbasic.customer.domain.Customer;
import com.blessing333.springbasic.customer.repository.JdbcTemplateCustomerRepository;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcTemplateCustomerRepositoryTest {

    @Autowired
    DataSource dataSource;
    EmbeddedMysql embeddedMysql;
    @Autowired
    private JdbcTemplateCustomerRepository repository;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-customer", classPathScript("schema.sql"))
                .start();
    }

    @AfterEach
    void resetDB(){
        repository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @DisplayName("새로운 고객을 저장할 수 있어야한다.")
    @Test
    void insertTest() {
        String customerName = "test";
        String customerEmail = "test@test.com";
        Customer customer = CustomerFactory.createCustomer(customerName, customerEmail);
        UUID customerId = customer.getCustomerId();

        repository.insert(customer);

        Optional<Customer> found = repository.findById(customerId);
        assertTrue(found.isPresent());
        assertThat(repository.count()).isEqualTo(1);
        assertThat(found.get().getName()).isEqualTo(customerName);
        assertThat(found.get().getEmail()).isEqualTo(customerEmail);
    }

    @DisplayName("고객을 저장할 때 이미 같은 email을 사용하는 고객이 있다면 예외를 발생시킨다.")
    @Test
    void insertWithDuplicatedEmailTest() {
        String sameEmail = "test@test.com";
        Customer first = CustomerFactory.createCustomer("first", sameEmail);
        Customer second = CustomerFactory.createCustomer("second", sameEmail);
        repository.insert(first);

        assertThrows(RuntimeException.class,()->repository.insert(second));
    }

    @DisplayName("고객 정보를 수정할 수 있어야 한다.")
    @Test
    void updateTest() {
        Customer beforeCustomer = saveCustomerToDB("before", "before@Email.com");
        UUID customerId = beforeCustomer.getCustomerId();

        String afterName = "after";
        String afterEmail = "after@Email.com";
        beforeCustomer.changeName(afterName);
        beforeCustomer.changeEmail(afterEmail);
        repository.update(beforeCustomer);

        Optional<Customer> found =  repository.findById(customerId);
        assertTrue(found.isPresent());
        Customer updatedCustomer = found.get();
        assertThat(updatedCustomer.getName()).isEqualTo(afterName);
        assertThat(updatedCustomer.getEmail()).isEqualTo(afterEmail);
    }

    @DisplayName("id로 고객 정보를 조회할 수 있어야 한다.")
    @Test
    void findByIdTest(){
        Customer firstCustomer = saveCustomerToDB("first","first@email.com");
        UUID firstId = firstCustomer.getCustomerId();
        Customer secondCustomer = saveCustomerToDB("second","second@email.com");
        UUID secondId = secondCustomer.getCustomerId();

        Optional<Customer> firstFound = repository.findById(firstId);
        Optional<Customer> secondFound = repository.findById(secondId);

        assertTrue(firstFound.isPresent());
        assertThat(firstFound.get().getName()).isEqualTo(firstCustomer.getName());
        assertThat(firstFound.get().getEmail()).isEqualTo(firstCustomer.getEmail());
        assertTrue(secondFound.isPresent());
        assertThat(secondFound.get().getName()).isEqualTo(secondCustomer.getName());
        assertThat(secondFound.get().getEmail()).isEqualTo(secondCustomer.getEmail());
    }

    @DisplayName("모든 고객의 정보를 조회할 수 있어야 한다.")
    @Test
    void findAllTest(){
        Customer firstCustomer = saveCustomerToDB("first","first@email.com");
        Customer secondCustomer = saveCustomerToDB("second","second@email.com");

        List<Customer> customerList = repository.findAll();

        assertThat(customerList).hasSize(2).contains(firstCustomer,secondCustomer);
    }

    @DisplayName("모든 고객의 정보를 조회할 떄 고객 정보가 없다면 빈 리스트를 반환한다.")
    @Test
    void findAllWhenNothingExist(){
        List<Customer> customerList = repository.findAll();
        assertNotNull(customerList);
        assertThat(customerList).isEmpty();
    }

    @DisplayName("저장된 모든 Customer row의 개수를 반환할 수 있어야 한다")
    @Test
    void countTest() {
        saveCustomerToDB("first","first@email.com");
        saveCustomerToDB("second","second@email.com");
        saveCustomerToDB("third","third@email.com");

        int result = repository.count();

        assertThat(result).isEqualTo(3);
    }
    @DisplayName("저장된 모든 고객 정보를 삭제할 수 있어야 한다")
    @Test
    void deleteAllTest() {
        saveCustomerToDB("first","first@email.com");
        saveCustomerToDB("second","second@email.com");
        saveCustomerToDB("third","third@email.com");

        repository.deleteAll();

        assertThat(repository.count()).isZero();
    }

    private Customer saveCustomerToDB(String name,String mail){
        Customer customer = CustomerFactory.createCustomer(name, mail);
        repository.insert(customer);
        return customer;
    }

    @Configuration
    @ComponentScan("com.blessing333.springbasic.customer")
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-customer")
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
}