package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.domain.Customer;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Map<UUID, Customer>> getAllBlackList() throws IOException;
}
