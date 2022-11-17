package com.prgrms.springbootbasic.user.blacklist;

import com.prgrms.springbootbasic.user.domain.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BlacklistManager {
  private final BlacklistStorage blacklistStorage;

  public BlacklistManager(BlacklistStorage blacklistStorage){
    this.blacklistStorage = blacklistStorage;
  }

  public List<User> list(){
    return blacklistStorage.findAll();
  }
}
