package org.prgrms.springbootbasic.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.springbootbasic.entity.Customer;
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
import java.util.*;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScripts;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerRepositoryImplTest {
    @Configuration
    @ComponentScan(basePackages = "org.prgrms.springbootbasic.repository")
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    CustomerRepositoryImpl customerRepository;

    EmbeddedMysql embeddedMysql;

    Map<UUID, Customer> customerMap = new HashMap<>();
    List<UUID> customerIdList = new ArrayList<>();

    @BeforeAll
    void setUp() {
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_mgmt", classPathScripts("schema.sql"))
                .start();

        for (int i = 0; i < 4; i++) {
            UUID customerId = UUID.randomUUID();
            customerIdList.add(customerId);

            Customer customer = new Customer.Builder()
                    .customerId(customerId)
                    .name("ch" + i)
                    .email("ch" + i + "@email.com")
                    .createdAt(LocalDateTime.now())
                    .lastLoginAt(LocalDateTime.now())
                    .build();
            customerMap.put(customerId, customer);
        }
    }

    @BeforeEach
    void beforeEach() {
        for (Customer customer : customerMap.values()) {
            customerRepository.insert(customer);
        }
    }

    @AfterEach
    void afterEach() {
        customerRepository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("추가 - 성공")
    void insert_success() {
        // given
        Customer newCustomer = new Customer.Builder()
                .customerId(UUID.randomUUID())
                .name("FeH2O")
                .email("FeH2OH2O@gmail.com")
                .createdAt(LocalDateTime.now().truncatedTo(SECONDS))
                .lastLoginAt(LocalDateTime.now().truncatedTo(SECONDS))
                .build();

        // when
        Customer feH2O = customerRepository.insert(newCustomer);

        // then
        assertThat(newCustomer, samePropertyValuesAs(feH2O));
    }

    @Test
    @DisplayName("id로 조회 - 성공")
    void testFindById_success() {
        assertThat(customerMap.get(customerIdList.get(0)), samePropertyValuesAs(customerRepository.findById(customerIdList.get(0)).get()));
        assertThat(customerMap.get(customerIdList.get(1)), samePropertyValuesAs(customerRepository.findById(customerIdList.get(1)).get()));
        assertThat(customerMap.get(customerIdList.get(2)), samePropertyValuesAs(customerRepository.findById(customerIdList.get(2)).get()));
        assertThat(customerMap.get(customerIdList.get(3)), samePropertyValuesAs(customerRepository.findById(customerIdList.get(3)).get()));
    }

    @Test
    @DisplayName("수정 - 성공")
    void testUpdate_success() {

        System.out.println(customerMap);
        System.out.println(customerIdList);

        // given
        Customer beforeUpdateCustomer = customerRepository.findById(customerIdList.get(0)).get();
        beforeUpdateCustomer.changeName("updatedName");

        // when
        customerRepository.update(beforeUpdateCustomer);

        // then
        Optional<Customer> updatedCustomer = customerRepository.findById(beforeUpdateCustomer.getCustomerId());
        assertThat(beforeUpdateCustomer, samePropertyValuesAs(updatedCustomer.get()));
    }

    @Test
    @DisplayName("모두 조회 - 성공")
    void testFindAll_success() {
        // when
        List<Customer> all = customerRepository.findAll();

//        System.out.println(customerMap.get(all.get(0).getCustomerId()).getCustomerId());
//        System.out.println(all.get(0).getCustomerId());

        // then
        assertThat(customerMap.get(all.get(0).getCustomerId()), samePropertyValuesAs(all.get(0)));
        assertThat(customerMap.get(all.get(1).getCustomerId()), samePropertyValuesAs(all.get(1)));
        assertThat(customerMap.get(all.get(2).getCustomerId()), samePropertyValuesAs(all.get(2)));
        assertThat(customerMap.get(all.get(3).getCustomerId()), samePropertyValuesAs(all.get(3)));
    }

    @Test
    @DisplayName("id로 삭제 - 성공")
    void testDeleteById() {
        // when
        customerRepository.deleteById(customerIdList.get(0));

        //then
        Optional<Customer> byId = customerRepository.findById(customerIdList.get(0));
        assertThat(byId, is(Optional.empty()));
    }

    @Test
    @DisplayName("모두 삭제 - 성공")
    void deleteAll() {
        // when
        customerRepository.deleteAll();

        //then
        List<Customer> all = customerRepository.findAll();

        assertThat(all, is(Collections.emptyList()));
    }


}