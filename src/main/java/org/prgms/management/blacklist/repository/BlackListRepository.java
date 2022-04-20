package org.prgms.management.blacklist.repository;

import org.prgms.management.blacklist.entity.Blacklist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO : DB에선 update, insert는 RuntimeException을 발생시키는건 어떨까
@Component
public interface BlackListRepository {
    // TODO : 매개변수로 Optional 객체를 사용하는게 좋을까?
    Optional<Blacklist> insert(Blacklist blacklist);

    List<Blacklist> findAll();

    Optional<Blacklist> findById(UUID blacklistId);

    Optional<Blacklist> findByCustomerId(UUID customerId);

    Optional<Blacklist> update(Blacklist blacklist);

    Optional<Blacklist> delete(Blacklist blacklist);

    void deleteAll();
}
