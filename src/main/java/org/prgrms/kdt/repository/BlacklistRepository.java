package org.prgrms.kdt.repository;

import java.util.List;
import org.prgrms.kdt.domain.Customer;

public interface BlacklistRepository {

  List<Customer> findAll();
}