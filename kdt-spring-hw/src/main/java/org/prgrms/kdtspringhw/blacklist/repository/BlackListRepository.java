package org.prgrms.kdtspringhw.blacklist.repository;

import org.prgrms.kdtspringhw.blacklist.BlackList;


import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface BlackListRepository {
    Optional<BlackList> findById(UUID voucherId);

    BlackList insert(BlackList blackList);

    Map<UUID, BlackList> returnAll();
}
