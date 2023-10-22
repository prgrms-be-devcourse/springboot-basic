package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.domain.Customer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<List<Customer>> getAllBlackList() throws IOException;
}
