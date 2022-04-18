package org.prgms.management.blacklist.service;

import org.prgms.management.blacklist.entity.Blacklist;
import org.prgms.management.blacklist.repository.BlackListRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlacklistService {
    private final BlackListRepository blackListRepository;

    public BlacklistService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public Map<UUID, Blacklist> getAll() {
        return blackListRepository.getAll();
    }

    public Optional<Blacklist> getById(UUID blacklistId) {
        return blackListRepository.getById(blacklistId);
    }

    public Optional<Blacklist> getByCustomerId(UUID customerId) {
        return blackListRepository.getByCustomerId(customerId);
    }

    public Optional<Blacklist> insert(Blacklist blacklist) {
        return blackListRepository.insert(blacklist);
    }

    public Optional<Blacklist> delete(UUID blacklistId) {
        return blackListRepository.delete(blacklistId);
    }

    public void deleteAll() {
        blackListRepository.deleteAll();
    }
}
