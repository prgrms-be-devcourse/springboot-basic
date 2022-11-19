package org.prgrms.kdtspringdemo.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.domain.customer.model.Customer;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringJUnitConfig
class CustomerRepositoryTest {
    @Container
    private static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8").withInitScript("schema.sql");
    static Customer newCustomer;
    static List<Customer> blackListCustomers = new ArrayList<>();
    static List<Customer> nonBlackListCustomers = new ArrayList<>();
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    DataSource dataSource;

    @BeforeAll
    static void setup() {
        newCustomer = new Customer(UUID.randomUUID(),
                "test-user",
                LocalDate.parse("1999-02-04"),
                "test-user@gmail.com",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)
        );
        for (int i = 0; i < 10; i++) {
            Customer nonBlackCustomer = new Customer(UUID.randomUUID(),
                    "test-user%d".formatted(i),
                    LocalDate.parse("1999-02-04"),
                    "test-uesr%d@gmail.com".formatted(i),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)
            );
            Customer blackCustomer = new Customer(UUID.randomUUID(),
                    "test-user%s%d".formatted("-black", i),
                    LocalDate.parse("1999-02-04"),
                    "test-uesr%s%d@gmail.com".formatted("-black", i),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                    true
            );
            blackListCustomers.add(blackCustomer);
            nonBlackListCustomers.add(nonBlackCustomer);
        }
//        embeddedMysql = anEmbeddedMysql().addSchema("order_mgnt",StringResclassPath("schema.sql")).start()

    }

    @BeforeEach
    void insertCustomers() {
        blackListCustomers.stream().forEach(customerRepository::insert);
    }

    @AfterEach
    void cleanup() {
        customerRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    //CREATE
    @Test
    @Order(2)
    @DisplayName("고객을 넣는다.")
    void insert() {
        customerRepository.insert(newCustomer);
        Optional<Customer> addedCustomer = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(addedCustomer.get()).isEqualTo(newCustomer);
    }

    @Test
    @DisplayName("이메일이 같은 고객은 넣지 못한다.")
    public void insertSameEmail() throws Exception {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("ID가 같은 고객은 넣지 못한다.")
    public void insertSameId() throws Exception {
        //given

        //when

        //then
    }

    //READ
    @Test
    @DisplayName("모든 고객을 찾는다.")
    void findAll() {
        //given

        //when
        List<Customer> customers = customerRepository.findAll();
        //then
        assertThat(customers.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("모든 고객을 찾을때, DB에 아무 데이터도 없다.")
    public void findAllButNoData() throws Exception {
        //given
        customerRepository.deleteAll();
        //when
        List<Customer> customers = customerRepository.findAll();
        //then
        assertThat(customers.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("id로 고객을 찾는다.")
    public void findById() {
        //given
        var customerId = blackListCustomers.get(1).getCustomerId();
        //when
        Optional<Customer> customer = customerRepository.findById(customerId);
        //then
        assertThat(customer.get()).isEqualTo(blackListCustomers.get(1));
    }

    @Test
    @DisplayName("id로 고객을 찾는다. (id가 잘못됨)")
    public void findByIdWithWrongId() {
        //when
        Optional<Customer> customer = customerRepository.findById(UUID.randomUUID());
        //then
        assertThat(customer.isEmpty()).isTrue();
    }

    //UPDATE
    @Test
    @DisplayName("업데이트를 한다.")
    public void update() throws Exception {
        //given
        customerRepository.insert(newCustomer);
        var updatedCustomer = new Customer(newCustomer.getCustomerId(), "updated-name", newCustomer.getBirth(), newCustomer.getEmail(), newCustomer.getCreatedAt());
        //when
        var updatedRow = customerRepository.update(updatedCustomer);
        //then
        assertThat(customerRepository.findById(newCustomer.getCustomerId()).get()).isEqualTo(updatedCustomer);
        assertThat(customerRepository.findById(newCustomer.getCustomerId()).get().getName()).isEqualTo("updated-name");
        assertThat(updatedRow).isEqualTo(1);
    }

    @Test
    @DisplayName("업데이트를 실패했다.(겹치는 id 가 없음)")
    public void updateFail() throws Exception {
        //given
        //when
        var updatedRow = customerRepository.update(newCustomer);
        //then
        assertThat(updatedRow).isEqualTo(0);
    }

    @Test
    @DisplayName("업데이트를 실패했다.(수정이 불가능한 필드를 수정)")
    public void updateFailCannotUpdateField() {

    }

    //DELETE
    @Test
    @DisplayName("id로 고객을 지운다.")
    void deleteById() {
        //given
        var customer1 = blackListCustomers.get(1);
        //when
        customerRepository.deleteById(customer1.getCustomerId());
        //then
        Optional<Customer> customer = customerRepository.findById(customer1.getCustomerId());
        assertThat(customer.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("id로 고객을 지운다 (id가 잘못됨)")
    public void deleteByIdWithWrongId() throws Exception {
        //given
        //when
        //then
        assertThat(customerRepository.deleteById(UUID.randomUUID())).isEqualTo(0);
    }

    @Test
    @DisplayName("모든 고객을 지운다.")
    void deleteAll() {
        var allRow = customerRepository.count();
        var deletedRow = customerRepository.deleteAll();
        assertThat(customerRepository.findAll().isEmpty()).isTrue();
        assertThat(deletedRow).isEqualTo(allRow);
    }


    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo.domain.customer.repository"})
    static class Config {
        @Bean
//        @ConfigurationProperties(prefix = "")
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(mySQLContainer.getJdbcUrl())
                    .username(mySQLContainer.getUsername())
                    .password(mySQLContainer.getPassword())
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
}