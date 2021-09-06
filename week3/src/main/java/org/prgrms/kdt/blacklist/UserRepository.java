package org.prgrms.kdt.blacklist;

import java.util.List;

public interface UserRepository {
    List<BlackUser> findAll() ;
}
