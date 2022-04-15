package com.voucher.vouchermanagement.service;

import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.repository.blacklist.BlacklistRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {

  private final BlacklistRepository blackListRepository;

  public BlacklistService(BlacklistRepository blackListRepository) {
    this.blackListRepository = blackListRepository;
  }

  public List<Customer> findAll() {
    List<Customer> foundBlacklist = blackListRepository.findAll();

    return foundBlacklist;
  }
}
