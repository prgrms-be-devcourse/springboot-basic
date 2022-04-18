package org.prgms.management.blacklist.repository;

import org.prgms.management.blacklist.entity.Blacklist;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO : DB에선 update, insert는 RuntimeException을 발생시키는건 어떨까
@Component
public interface BlackListRepository {
    Map<UUID, Blacklist> getAll();

    Optional<Blacklist> getById(UUID blacklistId);

    Optional<Blacklist> getByCustomerId(UUID customerId);

    // TODO : 매개변수로 Optional 객체를 사용하는게 좋을까?
    Optional<Blacklist> insert(Blacklist blacklist);

    Optional<Blacklist> update(Blacklist blacklist);

    Optional<Blacklist> delete(UUID blacklistId);

    void deleteAll();
}
