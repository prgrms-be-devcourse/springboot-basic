package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.util.UUIDUtils.toUUID;

@Primary
@Qualifier("jdbc")
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public List<Customer> findAllByCustomerType(CustomerType customerType) {
        return jdbcTemplate.query("SELECT * FROM customers WHERE customer_type = :customerType",
            Collections.singletonMap("customerType", customerType.toString()),
            customerRowMapper);
    }

    private RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String typeName = resultSet.getString("customer_type");
        CustomerType customerType = CustomerType.valueOf(typeName);

        return new Customer(customerId, customerName, customerType);
    };
}
