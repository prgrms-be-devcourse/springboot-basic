package com.kdt.commandLineApp.customer;

import java.io.IOException;
import java.util.List;

public interface CustomerRepository{
    public List<Customer> getAll() throws IOException;
}
