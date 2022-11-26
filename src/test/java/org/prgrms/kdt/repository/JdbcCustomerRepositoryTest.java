package org.prgrms.kdt.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.Customer;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.Optional;

public class JdbcCustomerRepositoryTest {
    private CustomerRepository repository;
    private JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepositoryTest() {
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/test")
                .username("root")
                .password("root")
                .type(MysqlDataSource.class)
                .build();
        Resource resource = new ClassPathResource("data.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repository = new JdbcCustomerRepository(new NamedParameterJdbcTemplate(jdbcTemplate));
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("insert into customer (email) values (\"asdf@naver.com\")");
    }

    @AfterEach
    void cleanUp() {
        jdbcTemplate.execute("truncate customer;");
    }

    @Test
    @DisplayName("[성공] 유저 저장하기")
    void save() {
        String email = "test@naver.com";
        Customer newCustomer = new Customer(email);

        Optional<Customer> returnedCustomer = repository.saveCustomer(newCustomer);

        Assertions.assertTrue(returnedCustomer.isPresent());
    }

    @Test
    @DisplayName("[성공] 유저 아이디로 조회하기")
    void getById() {
        long customerId = 1;

        Optional<Customer> customerById = repository.getCustomerById(customerId);

        Assertions.assertEquals(customerId, customerById.get().getId());
    }

    @Test
    @DisplayName("[실패] 존재하지 않는 유저 아이디로 조회하기")
    void getById_withInvalidId() {
        long invalidCustomerId = 0;

        Optional<Customer> customerById = repository.getCustomerById(invalidCustomerId);

        Assertions.assertFalse(customerById.isPresent());
    }
}
