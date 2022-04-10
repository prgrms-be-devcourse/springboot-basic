package org.prgrms.kdt.customer.service;

import java.util.List;
import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.customer.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {

  private static final String BLACKLIST_PREFIX = "Blacklist ";
  private static final String CUSTOMER_PREFIX = "Customer ";

  private final BlacklistRepository blacklistRepository;

  public BlacklistService(
      BlacklistRepository blacklistRepository) {
    this.blacklistRepository = blacklistRepository;
  }

  public List<String> findAll() {
    return blacklistRepository.findAll().stream()
        .map(Customer::toString)
        .map(s -> s.replace(CUSTOMER_PREFIX, BLACKLIST_PREFIX))
        .toList();
  }
}