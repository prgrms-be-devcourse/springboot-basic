package org.prgms.voucheradmin.domain.customer.dao;

import java.io.IOException;
import java.util.List;

import org.prgms.voucheradmin.domain.customer.entity.Customer;

public interface CustomerRepository {
    List<Customer> getAll() throws IOException;
}
