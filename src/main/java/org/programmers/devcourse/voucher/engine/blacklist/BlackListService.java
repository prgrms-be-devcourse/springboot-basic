package org.programmers.devcourse.voucher.engine.blacklist;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BlackListService {

  private final BlackListRepository repository;

  public BlackListService(
      BlackListRepository repository) {
    this.repository = repository;
  }


  public List<BlackList> getBlackList() {
    return repository.getAll();
  }
}
