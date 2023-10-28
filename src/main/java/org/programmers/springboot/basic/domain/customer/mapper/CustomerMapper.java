package org.programmers.springboot.basic.domain.customer.mapper;

import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.CustomerType;
import org.programmers.springboot.basic.util.converter.UUIDConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;

@Configuration
public class CustomerMapper {

    @Bean
    public RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            UUID customerId = UUIDConverter.toUUID(rs.getBytes("customer_id"));
            String name = rs.getString("name");
            String email = rs.getString("email");
            CustomerType customerType = CustomerType.valueOf(rs.getInt("isBlack"));
            return new Customer(customerId, name, email, customerType);
        };
    }
}
