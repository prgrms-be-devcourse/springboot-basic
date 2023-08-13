package org.promgrammers.voucher.util;

import org.promgrammers.voucher.domain.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


import java.util.UUID;

public class CustomerUtils {

    public static SqlParameterSource createParameterSource(Customer customer) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", customer.getId().toString())
                .addValue("username", customer.getUsername())
                .addValue("customerType", customer.getCustomerType().toString());

        return paramSource;
    }


    public static RowMapper<Customer> customerRowMapper = ((rs, rowNum) -> {
        String customerId = rs.getString("id");
        String username = rs.getString("username");

        return new Customer(UUID.fromString(customerId), username);
    });
}
