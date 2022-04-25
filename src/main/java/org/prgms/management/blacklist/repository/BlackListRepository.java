package org.prgms.management.blacklist.repository;

import org.prgms.management.blacklist.vo.Blacklist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface BlackListRepository {
    Optional<Blacklist> insert(Blacklist blacklist);

    List<Blacklist> findAll();

    Optional<Blacklist> findById(UUID blacklistId);

    Optional<Blacklist> findByCustomerId(UUID customerId);

    Optional<Blacklist> delete(Blacklist blacklist);

    void deleteAll();
}
