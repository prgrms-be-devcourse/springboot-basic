package org.prgms.management.blacklist.service;

import org.prgms.management.blacklist.entity.Blacklist;
import org.prgms.management.blacklist.repository.BlackListRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO : CRUD 구현
// TODO : Customer 클래스 생성 후 userId 대신 Customer 객체에 대응하는 메서드 구현
@Service
public class BlacklistService {
    private final BlackListRepository blackListRepository;

    public BlacklistService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public Map<UUID, Blacklist> getAllBlackList() {
        return blackListRepository.getAll();
    }

    public Optional<Blacklist> insertBlackList(Blacklist blacklist) {
        return blackListRepository.insert(blacklist);
    }
}
