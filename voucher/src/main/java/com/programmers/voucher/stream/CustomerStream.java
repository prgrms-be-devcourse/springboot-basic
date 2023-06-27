package com.programmers.voucher.stream;

import com.programmers.voucher.domain.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerStream {
    List<Customer> findAll() throws SQLException;

    Optional<Customer> findById(UUID customerId) throws SQLException;

    UUID save(Customer customer) throws SQLException;

    UUID update(UUID customerId, String name) throws SQLException;

    void deleteById(UUID customerId) throws SQLException;

}
