package com.prgms.springbootbasic.persistent;

import com.prgms.springbootbasic.domain.Customer;
import com.prgms.springbootbasic.util.BinaryToUUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

@Repository
public class CustomersInDB {

    private static final String FIND_ALL = "select * from customers";

    private final RowMapper<Customer> customerMapper = (ResultSet resultSet, int i) -> {
        UUID customerId = BinaryToUUID.biToUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        return new Customer(customerId, name);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomersInDB(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> findAll() {
        List<Customer> customers = jdbcTemplate.query(FIND_ALL, customerMapper);
        return customers;
    }

}
