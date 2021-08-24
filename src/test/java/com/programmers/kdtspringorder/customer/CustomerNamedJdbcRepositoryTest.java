package com.programmers.kdtspringorder.customer;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
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
import java.util.List;
import java.util.Optional;
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
class CustomerNamedJdbcRepositoryTest {

    @Configuration
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

            System.out.println("dataSource = " + dataSource);

            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
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

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp(){
        testUser = new Customer(UUID.randomUUID(), "testUser", "test@naver.com", LocalDateTime.parse(LocalDateTime.now().toString().substring(0,26)));

        // embedded mysql 설정인데 실행 안됨
//        MysqldConfig mySqlConfig = aMysqldConfig(v8_0_11)
//                .withCharset(UTF8)
//                .withPort(2215)
//                .withUser("user", "user")
//                .withTimeZone("Asia/Seoul")
//                .build();
//
//        embeddedMysql = anEmbeddedMysql(mySqlConfig)
//                .addSchema("order_mgmt", classPathScript("schema.sql"))
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
    public void testHikariConnectionPool() throws Exception {
//        System.out.println(dataSource);
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
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
    public void testFindAll() throws Exception {
        final List<Customer> list = customerJdbcRepository.findAll();
        assertThat(list.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다")
    public void testFindByName() throws Exception {
        Optional<Customer> acutal = customerJdbcRepository.findByName(testUser.getName());
        assertThat(acutal.isEmpty(), is(false));

        acutal = customerJdbcRepository.findByName("s");
        assertThat(acutal.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다")
    public void testFindByEmail() throws Exception {
        Optional<Customer> acutal = customerJdbcRepository.findByEmail(testUser.getEmail());
        assertThat(acutal.isEmpty(), is(false));

        acutal = customerJdbcRepository.findByEmail("s");
        assertThat(acutal.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() throws Exception {
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