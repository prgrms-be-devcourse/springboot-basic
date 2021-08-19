package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CsvCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> blacklist = new ConcurrentHashMap<>();

    @Override
    public List<Customer> getBlacklist() {
        return null;
    }
}
