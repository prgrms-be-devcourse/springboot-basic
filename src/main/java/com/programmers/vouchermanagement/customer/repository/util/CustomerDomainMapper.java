package com.programmers.vouchermanagement.customer.repository.util;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.util.DomainMapper;
import org.springframework.jdbc.core.RowMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.programmers.vouchermanagement.util.Constant.COMMA_SEPARATOR;

public class CustomerDomainMapper extends DomainMapper {
    public static final String BLACK_KEY = "black";
    public static final String NAME_KEY = "name";
    public static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes(ID_KEY));
        String name = resultSet.getString(NAME_KEY);
        boolean isBlack = resultSet.getBoolean(BLACK_KEY);

        return new Customer(id, name, isBlack);
    };

    private CustomerDomainMapper() {
    }

    public static Map<String, Object> customerToParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID_KEY, customer.getId().toString().getBytes());
        paramMap.put(NAME_KEY, customer.getName());
        paramMap.put(BLACK_KEY, customer.isBlack());
        return paramMap;
    }

    public static Customer stringToCustomer(String line) {
        String[] customerInfo = line.split(COMMA_SEPARATOR);
        UUID customerId = UUID.fromString(customerInfo[0]);
        String name = customerInfo[1];
        boolean isBlack = Boolean.parseBoolean(customerInfo[2]);
        return new Customer(customerId, name, isBlack);
    }

    public static String customerToString(Customer customer) {
        return customer.getId().toString() + COMMA_SEPARATOR
                + customer.getName() + COMMA_SEPARATOR
                + customer.isBlack();
    }
}
