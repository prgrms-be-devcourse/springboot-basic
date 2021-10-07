package org.prgrms.orderApp.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringJUnitConfig
class CustomerNameJdbcRepositoryTest {
    private static Logger logger = LoggerFactory.getLogger(CustomerNameJdbcRepositoryTest.class);


    @Configuration
    @EnableTransactionManagement
    @ComponentScan( basePackages = {"org.prgrms.orderApp.customer"})
     static class Config{
        @Bean
        public DataSource dataSource(){

            var dataSource =  DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt_w3")
                    .username("root")
                    .password("root1234!")
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
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager){
            return new TransactionTemplate(platformTransactionManager);
        }


    }

    @Autowired
    CustomerNameJdbcRepository customerNameJdbcRepository;

    @Autowired
    DataSource dataSource;


    final Customer defaultDataToInsert = new Customer(UUID.randomUUID(), "test_0907", "test@test.com", LocalDateTime.now());

    @AfterEach
    void deleteAll(){
        customerNameJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 고객을 조회할 수 있다.")
    void findAllTest() {
        // Given
        customerNameJdbcRepository.insert(defaultDataToInsert);

        // When
        var allCustomers = customerNameJdbcRepository.findAll();

        // Then
        assertThat(allCustomers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("한 명의 고객의 정보를 저장할 수 있다.")
    void insertTest() {
        // Given, When
        var dataInserted = customerNameJdbcRepository.insert(defaultDataToInsert);

        // Then
        assertThat(dataInserted, equalTo(defaultDataToInsert));
    }

    @Test
    @DisplayName("동일한 이메일을 저장할 수 없다.")
    void NotDuplicateTest() {
        //Given
        var dataToInsert = new Customer(UUID.randomUUID(), "test_0907", "default@test.com", LocalDateTime.now());
        customerNameJdbcRepository.insert(dataToInsert);

        // When , Then
        assertThrows(DuplicateKeyException.class, () -> {
            customerNameJdbcRepository.insert(dataToInsert);
        });

    }

    @Test
    @DisplayName("고객 이름을 변경할 수 있다. ")
    void updateTest() {
        // Given
        var customerId = UUID.randomUUID();
        var dataToInsert = new Customer(customerId, "test_0907", "default@test.com", LocalDateTime.now());
        customerNameJdbcRepository.insert(dataToInsert);

        var dataToUpdate = customerNameJdbcRepository.findById(customerId).get();
        var updateValue = "updateName";
        dataToUpdate.changeName(updateValue);


        var updatedData = customerNameJdbcRepository.update(dataToUpdate);
        assertThat(updatedData.getName(), is(updateValue));
        assertThat(updatedData.getName(), not(dataToInsert.getName()));
    }

    @Test
    @DisplayName("customer id를 이용하여, 데이터를 추출할 수 있다.")
    void findByIdTest() {
        // Given
        var customerId = UUID.randomUUID();
        var email = "default@test.com";
        var dataToInsert = new Customer(customerId, "test_0907", email, LocalDateTime.now());
        customerNameJdbcRepository.insert(dataToInsert);

        // When
        var selectedData = customerNameJdbcRepository.findById(customerId);

        // Then
        assertThat(selectedData.get().getEmail(), is(email));
    }

    @Test
    @DisplayName("customer name를 이용하여, 데이터를 추출할 수 있다.")
    void findByNameTest() {
        // Given
        var name = "test_0907";
        var email = "default@test.com";
        var dataToInsert = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        customerNameJdbcRepository.insert(dataToInsert);

        // When
        var selectedData = customerNameJdbcRepository.findByName(name);

        // Then
        assertThat(selectedData.get().getName(), is(name));
    }

    @Test
    @DisplayName("customer email를 이용하여, 데이터를 추출할 수 있다.")
    void findByEmailTest() {
        // Given
        var customerId = UUID.randomUUID();
        var email = "default@test.com";
        var dataToInsert = new Customer(customerId, "test_0907", email, LocalDateTime.now());
        customerNameJdbcRepository.insert(dataToInsert);

        // When
        var selectedData = customerNameJdbcRepository.findByEmail(email);

        // Then
        assertThat(selectedData.get().getCustomerId(), is(customerId));
    }

    @Test
    @DisplayName("저장된 모든 데이터의 수를 파악할 수 있다.")
    void countTest() {
        // Given
        customerNameJdbcRepository.insert(defaultDataToInsert);

        // When
        var count = customerNameJdbcRepository.count();

        // Then
        assertThat(count, is(1));

    }


}