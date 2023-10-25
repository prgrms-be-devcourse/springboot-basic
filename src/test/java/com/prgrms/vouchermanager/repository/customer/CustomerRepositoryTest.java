package com.prgrms.vouchermanager.repository.customer;

import com.prgrms.vouchermanager.domain.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@SpringJUnitConfig
class CustomerRepositoryTest {

    private CustomerRepository customerRepository;
    @Autowired
    private JdbcTemplate template;
    @Autowired private DataSource dataSource;
    private final Customer customer1 = new Customer("스카라무슈", 1995);
    private final Customer customer2 = new Customer("종려", 1990);

    @Configuration
    static class TestConfig {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url("jdbc:mysql://localhost:3306/voucher_manager?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
                    .username("root")
                    .password("suzzingV1999@")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }

    @BeforeEach
    void beforeEach() {
        customerRepository = new CustomerRepository(dataSource, new BlacklistFileRepository("src/main/resources/customer_blacklist.csv"));
        customerRepository.create(customer2);
    }

    @AfterEach
    void afterEach() {
        template.execute(" delete from customers;");
    }

    @Test
    @DisplayName("create")
    void create() {
        Customer customer = customerRepository.create(customer1);

        Assertions.assertThat(customer.getName()).isEqualTo("스카라무슈");
        Assertions.assertThat(customer.getYearOfBirth()).isEqualTo(1995);
    }

    @Test
    @DisplayName("list")
    void list() {
        List<Customer> list = customerRepository.list();

        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("findById")
    void findById() {
        Customer customer = customerRepository.create(customer1);
        Customer findVoucher = customerRepository.findById(customer.getId());

        Assertions.assertThat(findVoucher.getName()).isEqualTo(customer.getName());
        Assertions.assertThat(findVoucher.getYearOfBirth()).isEqualTo(customer.getYearOfBirth());
    }

    @Test
    @DisplayName("updateYearOfBirth")
    void updateYearOfBirth() {
        customerRepository.updateYearOfBirth(customer2.getId(), 2000);
        Customer updateVoucher = template.queryForObject("select * from customers where customer_id=UUID_TO_BIN(?)",
                customerRowMapper(),
                customer2.getId().toString().getBytes());
        Assertions.assertThat(updateVoucher.getYearOfBirth()).isEqualTo(2000);
    }

    @Test
    @DisplayName("updateName")
    void updateName() {
        customerRepository.updateName(customer2.getId(), "벤티");
        Customer updateVoucher = template.queryForObject("select * from customers where customer_id=UUID_TO_BIN(?)",
                customerRowMapper(),
                customer2.getId().toString().getBytes());
        Assertions.assertThat(updateVoucher.getName()).isEqualTo("벤티");
    }

    @Test
    @DisplayName("delete")
    void delete() {
        Assertions.assertThat(customerRepository.delete(customer2.getId())).isEqualTo(1);
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {

            return new Customer(convertBytesToUUID(rs.getBytes("customer_id")),
                    rs.getString("name"),
                    rs.getInt("year_of_birth"),
                    rs.getBoolean("is_blacklist"));
        };
    }

    private UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
