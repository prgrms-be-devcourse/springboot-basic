package org.prgrms.orderApp.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerNameJdbcRepositoryTest {
    private static Logger logger = LoggerFactory.getLogger(CustomerNameJdbcRepositoryTest.class);


    @Configuration
    @EnableTransactionManagement
    //@ActiveProfiles("local")
    @ComponentScan( basePackages = {"org.prgrms.orderApp.customer"})
     static class Config{
        @Bean
        public DataSource dataSource(){

            var dataSource =  DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt")
                    .username("root")
                    .password("root1234!")
                    .type(HikariDataSource.class)
                    .build();
//            dataSource.setMaximumPoolSize(100);
//            dataSource.setMinimumIdle(13);
            return dataSource;

        }
        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
            return new NamedParameterJdbcTemplate(dataSource);
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


    @Test
    @DisplayName("모든 고객을 조회할 수 있다.")
    void findAll() {
        var allCustomers = customerNameJdbcRepository.findAll();
        assertThat(allCustomers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("한 명의 고객의 정보를 저장할 수 있다.")
    void insert() {
        var dataToInsert = new Customer(UUID.randomUUID(), "test_0907", "test@test.com", LocalDateTime.now());
        var dataInserted = customerNameJdbcRepository.insert(dataToInsert);
        assertThat(dataInserted, equalTo(dataToInsert));
    }

    @Test
    @DisplayName("동일한 이메일을 저장할 수 없다. ")
    void NotDuplicate() {
        var dataToInsert = new Customer(UUID.randomUUID(), "test_0907", "test@test.com", LocalDateTime.now());
        var dataInserted = customerNameJdbcRepository.insert(dataToInsert);
        assertThat(dataInserted.getEmail(), emptyOrNullString()); // 테스트 확인 불가, 중보키 에러는 발생 확인
    }

    @Test
    @DisplayName("고객 이름을 변경할 수 있다. ")
    void update() {
        var updateValue = "update_name";
        var selectedData = customerNameJdbcRepository.findById(UUID.fromString("2d093557-0b0d-11ec-b3ad-5092eae0a28a"));
        var updatedData = customerNameJdbcRepository.update(new Customer(UUID.fromString("2d093557-0b0d-11ec-b3ad-5092eae0a28a"), updateValue, "test1@test.com", selectedData.get().getCreatedAt(), LocalDateTime.now()));
        assertThat(updatedData.getName(), is(updateValue));
    }

    @Test
    @DisplayName("한 명의 고객의 정보를 저장할 수 있다.")
    void findById() {
        var selectedData = customerNameJdbcRepository.findById(UUID.fromString("2d093557-0b0d-11ec-b3ad-5092eae0a28a"));
        assertThat(selectedData.isPresent(), is(true));
    }

    @Test
    @DisplayName("한 명의 고객의 정보를 저장할 수 있다.")
    void findByName() {
    }

    @Test
    @DisplayName("한 명의 고객의 정보를 저장할 수 있다.")
    void findByEmail() {
    }

    @Test
    @DisplayName("한 명의 고객의 정보를 저장할 수 있다.")
    void count() {
    }
}