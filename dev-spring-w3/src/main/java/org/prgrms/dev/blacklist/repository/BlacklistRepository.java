package org.prgrms.dev.blacklist.repository;

import org.prgrms.dev.blacklist.domain.Blacklist;

import java.util.List;

public interface BlacklistRepository {
    List<Blacklist> findAllInBlackList();
}
