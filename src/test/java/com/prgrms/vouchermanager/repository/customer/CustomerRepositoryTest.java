package com.prgrms.vouchermanager.repository.customer;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
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

    private CustomerRepository repository;
    @Autowired
    private JdbcTemplate template;
    @Autowired private DataSource dataSource;
    private final com.prgrms.vouchermanager.domain.customer.Customer customer1 = new com.prgrms.vouchermanager.domain.customer.Customer("스카라무슈", 1995);
    private final com.prgrms.vouchermanager.domain.customer.Customer customer2 = new com.prgrms.vouchermanager.domain.customer.Customer("종려", 1990);
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
        repository = new CustomerRepository(dataSource, new BlacklistFileRepository("src/main/resources/customer_blacklist.csv"));
        repository.create(customer2);
    }
    @AfterEach
    void afterEach() {
        repository.delete(UUID.fromString("70754a2f-d87d-4f69-af71-1d4bfe855e28"));
        repository.delete(UUID.fromString("626b8d5d-3940-4a0d-a3e4-fe6b297e8ad0"));
        repository.delete(UUID.fromString("8213dfa7-d577-4bb5-86d6-0159b3383f0e"));
        repository.delete(customer1.getId());
        repository.delete(customer2.getId());
    }

    @Test
    @DisplayName("create")
    void create() {
        com.prgrms.vouchermanager.domain.customer.Customer createCustomer = repository.create(customer1);
        Assertions.assertThat(createCustomer).isSameAs(customer1);
    }

    @Test
    @DisplayName("list")
    void list() {
        List<Customer> list = repository.list();
        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("findById")
    void findById() {
        Customer customer = repository.create(customer1);
        Customer findVoucher = repository.findById(customer.getId());

        Assertions.assertThat(findVoucher.getName()).isEqualTo(customer.getName());
        Assertions.assertThat(findVoucher.getYearOfBirth()).isEqualTo(customer.getYearOfBirth());
    }

    @Test
    @DisplayName("updateYearOfBirth")
    void updateYearOfBirth() {
        repository.updateYearOfBirth(customer2.getId(), 2000);
        Customer updateVoucher = template.queryForObject("select * from customers where customer_id=UUID_TO_BIN(?)",
                customerRowMapper(),
                customer2.getId().toString().getBytes());
        Assertions.assertThat(updateVoucher.getYearOfBirth()).isEqualTo(2000);
    }

    @Test
    @DisplayName("updateName")
    void updateName() {
        repository.updateName(customer2.getId(), "벤티");
        Customer updateVoucher = template.queryForObject("select * from customers where customer_id=UUID_TO_BIN(?)",
                customerRowMapper(),
                customer2.getId().toString().getBytes());
        Assertions.assertThat(updateVoucher.getName()).isEqualTo("벤티");
    }

    @Test
    @DisplayName("delete")
    void delete() {
        UUID deleteId = repository.delete(customer2.getId());
        Assertions.assertThat(deleteId).isEqualTo(customer2.getId());
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
