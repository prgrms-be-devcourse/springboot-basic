package org.prgrms.vouchermanager.domain.blacklist.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlacklistRepository {
    Optional<Blacklist> findById(UUID blacklistId);

    List<Blacklist> getAll();

    Optional<Blacklist> findByEmail(String email);
}
