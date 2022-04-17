package org.prgms.management.blacklist.repository;

import org.prgms.management.blacklist.entity.Blacklist;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO : CRUD 구현
// TODO : update, insert는 RuntimeException을 발생시키는건 어떨까
// TODO : 그 외에는 전부 try catch로 log를 기록한다.
public interface BlackListRepository {
    Map<UUID, Blacklist> getAll();

    // TODO : 매개변수로 Optional 객체를 사용하는게 좋을까?
    Optional<Blacklist> insert(Blacklist blacklist);
}
