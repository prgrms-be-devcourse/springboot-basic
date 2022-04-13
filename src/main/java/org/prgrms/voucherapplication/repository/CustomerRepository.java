package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.entity.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerRepository {

    List<Customer> findAll() throws IOException;
}
