package org.programmers.springbootbasic.customer.repository;

import org.programmers.springbootbasic.customer.domain.Customer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateCustomerRepository implements CustomerRepository {
    @Override
    public Customer insert(Customer customer) {
//        String sql = "insert into customer(??ID, NAME, EMAIL, SINGED_UP_AT) values (?, ?, ?)";
//        Map<String, Object> parameters = convertToParameterList(customer);
//        int updatedRow = jdbcTemplate.update(sql, parameters.get("voucher_id"), parameters.get("amount"), parameters.get("type"));
//        if (updatedRow != 1) {
//            throw new IllegalStateException("바우처가 정상적으로 저장되지 않았습니다.");
//        }
        return customer;
    }
    /**
     CUSTOMER_ID   BIGINT PRIMARY KEY,
     NAME          varchar(20) NOT NULL,
     EMAIL         varchar(50) NOT NULL,
     LAST_LOGIN_AT datetime(6)          DEFAULT NULL,
     SIGNED_UP_AT  datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
     CONSTRAINT unq_user_email UNIQUE (email)

     */

    @Override
    public Optional<Customer> findById(Long customerId) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public void remove(Long customerId) {

    }
}
