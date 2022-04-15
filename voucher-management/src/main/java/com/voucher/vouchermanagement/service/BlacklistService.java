package com.voucher.vouchermanagement.service;

import com.voucher.vouchermanagement.model.user.User;
import com.voucher.vouchermanagement.repository.blacklist.BlacklistRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {

  private final BlacklistRepository blackListRepository;

  public BlacklistService(BlacklistRepository blackListRepository) {
    this.blackListRepository = blackListRepository;
  }

  public List<User> findAll() throws IOException {
    List<User> foundBlacklist = blackListRepository.findAll();

    return foundBlacklist;
  }
}
