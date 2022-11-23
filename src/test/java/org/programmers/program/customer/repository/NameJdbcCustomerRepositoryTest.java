package org.programmers.program.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.program.customer.model.Customer;
import org.programmers.program.customer.service.CustomerService;
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

import java.util.UUID;
import java.util.concurrent.CountedCompleter;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NameJdbcCustomerRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = "org.programmers.program.customer")
    static class Config{
//        @Bean
//        public DataSource dataSource(){
//            return DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost:2215/customers")
//                    .username("test")
//                    .password("test1234!")
//                    .type(HikariDataSource.class)
//                    .build();
//        }
        @Bean
        public DataSource dataSource(){
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucherDB")
                    .username("root")
                    .password("voucher1234!")
                    .type(HikariDataSource.class)
                    .build();
        }


        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
//        @Bean
//        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
//            return new DataSourceTransactionManager(dataSource);
//        }
//
//        @Bean
//        public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager){
//            return new TransactionTemplate(transactionManager);
//        }
    }

    @Autowired
    NameJdbcCustomerRepository repository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;

//    @BeforeAll
//    void setUp(){
//        var mysqlConfig = aMysqldConfig(v8_0_11)
//                .withCharset(UTF8)
//                .withPort(2215)
//                .withUser("test", "test1234!")
//                .withTimeZone("Asia/Seoul")
//                .build();
//        embeddedMysql = anEmbeddedMysql(mysqlConfig)
//                .addSchema("customers", classPathScript("customerSchema.sql"))
//                .start();
//    }
//
//    @AfterAll
//    void cleanUp(){
//        embeddedMysql.stop();
//    }

    @Test
    @Order(1)
    @DisplayName("히카리 DB 소스인지 확인")
    void testHikariConnectionPool(){
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("데이터 삽입 테스트")
    void insertTest(){
        var id = UUID.randomUUID();
        repository.insert(new Customer(id, "test1@email.com", "test1"));
        var retrievedCustomer = repository.findById(id);

        assertThat(retrievedCustomer).isNotEmpty();
        assertThat(retrievedCustomer.get().getId()).isEqualTo(id);
    }

    @Test
    @Order(3)
    @DisplayName("id, email, 이름으로 찾기 테스트")
    void findByTest(){
        var id2 = UUID.randomUUID();
        var email2 = "test2@email.com";
        var name2 = "test2";

        repository.insert(new Customer(id2, email2, name2));
        var byEmail = repository.findByEmail(email2);
        var byName = repository.findByName(name2);

        assertThat(byEmail).isNotEmpty();
        assertThat(byName).isNotEmpty();

        assertThat(byEmail.get().getEmail()).isEqualTo(email2);
        assertThat(byName.get().getName()).isEqualTo(name2);
    }

    @Test
    @Order(4)
    @DisplayName("수정 테스트")
    void updateTest(){
        var id = UUID.randomUUID();
        repository.insert(new Customer(id, "testtest@email.com", "testtest"));
        repository.update(new Customer(id, "changed@email.com", "changed"));

        assertThat(repository.findById(id).get().getName()).isEqualTo("changed");
    }

    @Test
    @Order(5)
    @DisplayName("find all, count 테스트")
    void findAllCountTest(){
        var allCustomers = repository.findAll();
        var customersCnt = repository.count();

        assertThat(allCustomers).hasSize(customersCnt);
    }

    @Test
    @Order(6)
    @DisplayName("전부다 지우기 테스트")
    void deleteAllTest(){
        repository.deleteAll();
        var allCustomers = repository.findAll();
        var cnt = repository.count();

        assertThat(allCustomers).isEmpty();
        assertThat(cnt).isZero();
    }
}