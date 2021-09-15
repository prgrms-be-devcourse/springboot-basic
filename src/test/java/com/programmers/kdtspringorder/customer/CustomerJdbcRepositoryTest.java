package com.programmers.kdtspringorder.customer;

import com.programmers.kdtspringorder.customer.model.Customer;
import com.programmers.kdtspringorder.customer.repository.CustomerJdbcRepository;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @TestConfiguration
    @ComponentScan(
            basePackages = {"com.programmers.kdtspringorder"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("user")
                    .password("user")
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
    }

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer testUser;
    Customer testUser2;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        testUser = new Customer(UUID.randomUUID(), "testUser", "test@naver.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        testUser2 = new Customer(UUID.randomUUID(), "testUser2", "test2@naver.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
//        MysqldConfig mySqlConfig = aMysqldConfig(v8_0_11)
//                .withCharset(UTF8)
//                .withPort(2215)
//                .withUser("user", "user")
//                .withTimeZone("Asia/Seoul")
//                .build();
//
//        embeddedMysql = anEmbeddedMysql(mySqlConfig)
//                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
//                .start();

//        customerJdbcRepository.deleteAll();
    }

//    @AfterAll
//    @Disabled
//    void cleanUp(){
//        embeddedMysql.stop();
//    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
//        System.out.println(dataSource);
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
        customerJdbcRepository.deleteAll();
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다")
    public void testInsert() throws Exception {
        customerJdbcRepository.insert(testUser);

        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(testUser.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(testUser));
    }


    @Test
    @DisplayName("DB에 있는 모든 리스트 조회")
    public void testFindAll() {
        final List<Customer> list = customerJdbcRepository.findAll();
        assertThat(list.isEmpty(), is(false));
        Assertions.assertThat(list).containsExactly(testUser, testUser2);
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다")
    public void testFindByName() {
        Optional<Customer> acutal = customerJdbcRepository.findByName(testUser.getName());
        assertThat(acutal.isEmpty(), is(false));

        acutal = customerJdbcRepository.findByName("s");
        assertThat(acutal.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다")
    public void testFindByEmail() {
        Optional<Customer> acutal = customerJdbcRepository.findByEmail(testUser.getEmail());
        assertThat(acutal.isEmpty(), is(false));

        acutal = customerJdbcRepository.findByEmail("s");
        assertThat(acutal.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() {
        testUser.changeName("updated-user");
        customerJdbcRepository.update(testUser);

        List<Customer> all = customerJdbcRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(testUser)));

        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(testUser.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(testUser));
    }


}