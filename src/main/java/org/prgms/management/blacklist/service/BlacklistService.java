package org.prgms.management.blacklist.service;

import org.prgms.management.blacklist.entity.Blacklist;
import org.prgms.management.blacklist.repository.BlackListRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class BlacklistService {
    private final BlackListRepository blackListRepository;

    public BlacklistService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public Map<UUID, Blacklist> getAllBlackList() {
        return blackListRepository.getAll();
    }
}
