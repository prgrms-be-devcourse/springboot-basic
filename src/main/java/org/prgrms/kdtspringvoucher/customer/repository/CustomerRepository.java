package org.prgrms.kdtspringvoucher.customer.repository;

import org.prgrms.kdtspringvoucher.customer.entity.JDBCCustomer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    JDBCCustomer insert(JDBCCustomer customer);
    JDBCCustomer update(JDBCCustomer customer);
    List<JDBCCustomer> findAll();
    Optional<JDBCCustomer> findById(UUID customerId);
    Optional<JDBCCustomer> findByName(String name);
    Optional<JDBCCustomer> findByEmail(String email);
    void deleteAll();
    int count();
}
