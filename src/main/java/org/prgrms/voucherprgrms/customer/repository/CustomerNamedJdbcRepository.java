package org.prgrms.voucherprgrms.customer.repository;

import org.prgrms.voucherprgrms.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Qualifier("named")
public class CustomerNamedJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {
            {
                put("customerId", customer.getCustomerId());
                put("name", customer.getName());
                put("email", customer.getEmail());
                put("voucher", customer.getVoucher().isPresent() ? customer.getVoucher().get().getVoucherId() : null);
            }
        };
    }

    @Override
    public Customer insert(Customer customer) {
        jdbcTemplate.update("insert into CUSTOMERS(customer_id, name, email) values(UUID_TO_BIN(:customerId),:name,:email)",
                toParamMap(customer));

        //TODO insert exception 처리

        return customer;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {

        return null;
    }

}
