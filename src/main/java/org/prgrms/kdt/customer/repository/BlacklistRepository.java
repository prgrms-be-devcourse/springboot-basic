package org.prgrms.kdt.customer.repository;

import java.util.List;
import org.prgrms.kdt.customer.domain.Customer;

public interface BlacklistRepository {

  List<Customer> findAll();
}