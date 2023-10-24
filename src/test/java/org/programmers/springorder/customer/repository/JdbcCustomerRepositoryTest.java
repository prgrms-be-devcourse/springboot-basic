package org.programmers.springorder.customer.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.model.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcCustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;


    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("customerName", customer.getName());
            put("customerType", customer.getCustomerType().name());
        }};
    }

    @Test
    @DisplayName("회원 저장에 성공한다.")
    void save() {
        UUID customerId = UUID.randomUUID();
        String customerName = "홍길동";
        CustomerType customerType = CustomerType.NORMAL;

        Customer customer = Customer.toCustomer(customerId, customerName, customerType);
        jdbcTemplate.update("INSERT INTO customers(customer_id, customer_name, customer_type) VALUES(:customerId, :customerName, :customerType)", toParamMap(customer));
    }

    @Test
    @DisplayName("전체 회원 조회에 성공한다.")
    void findAll() {

        List<Customer> customerList = customerRepository.findAll();

        // then
        assertThat(customerList).hasSize(1);
    }

}