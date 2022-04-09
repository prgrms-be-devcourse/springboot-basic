package org.prgms.voucheradmin.domain.customer.dao;

import org.prgms.voucheradmin.domain.customer.entity.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerRepository {
    List<Customer> getAll() throws IOException;
}
