package com.kdt.commandLineApp.service.customer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository{
    public List<Customer> getAllBlacklist() throws IOException;

    public List<Customer> getAll();

    public Optional<Customer> get(long id);

    public void add(Customer customer);
}
