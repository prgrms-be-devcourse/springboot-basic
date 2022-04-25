package org.prgms.management.blacklist.service;

import org.prgms.management.blacklist.vo.Blacklist;
import org.prgms.management.blacklist.repository.BlackListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlacklistService {
    private final BlackListRepository blackListRepository;

    public BlacklistService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public Optional<Blacklist> insert(Blacklist blacklist) {
        return blackListRepository.insert(blacklist);
    }

    public List<Blacklist> getAll() {
        return blackListRepository.findAll();
    }

    public Optional<Blacklist> getById(UUID blacklistId) {
        return blackListRepository.findById(blacklistId);
    }

    public Optional<Blacklist> getByCustomerId(UUID customerId) {
        return blackListRepository.findByCustomerId(customerId);
    }

    public Optional<Blacklist> delete(Blacklist blacklist) {
        return blackListRepository.delete(blacklist);
    }

    public void deleteAll() {
        blackListRepository.deleteAll();
    }
}
