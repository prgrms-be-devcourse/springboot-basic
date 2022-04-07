package com.voucher.vouchermanagement.service;

import com.voucher.vouchermanagement.model.user.User;
import com.voucher.vouchermanagement.repository.blacklist.BlacklistRepository;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {

  private final BlacklistRepository blackListRepository;
  private static final Logger logger = LoggerFactory.getLogger(BlacklistService.class);

  public BlacklistService(BlacklistRepository blackListRepository) {
    this.blackListRepository = blackListRepository;
  }

  public List<User> findAll() throws IOException {
    List<User> foundBlacklist = blackListRepository.findAll();
    logger.info("findAll() ");
    foundBlacklist.stream().forEach(user -> logger.info(user.toString()));

    return foundBlacklist;
  }
}
