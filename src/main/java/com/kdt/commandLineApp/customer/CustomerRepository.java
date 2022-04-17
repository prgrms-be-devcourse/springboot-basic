package com.kdt.commandLineApp.customer;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CustomerRepository{
    public List<Customer> getAllBlacklist() throws IOException;
    public List<Customer> getCustomers(UUID voucherId);
}
