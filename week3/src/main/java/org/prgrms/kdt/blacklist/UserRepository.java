package org.prgrms.kdt.blacklist;

import org.prgrms.kdt.blacklist.BlackUser;

import java.util.List;

public interface UserRepository {
    List<BlackUser> findAll() ;
}
