package com.programmers.customer.repository.sql;

import com.programmers.customer.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListCustomerResultSetExtractor implements ResultSetExtractor<List<Customer>> {
    private final CustomerResultSetExtractor resultSetExtractor;

    public ListCustomerResultSetExtractor(CustomerResultSetExtractor resultSetExtractor) {
        this.resultSetExtractor = resultSetExtractor;
    }

    @Override
    public List<Customer> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Customer> customers = new ArrayList<>();

        while (!rs.isLast()) {
            Customer customer = resultSetExtractor.extractData(rs);
            customers.add(customer);
        }
        return customers;
    }
}

