package org.prgms.management.blacklist.service;

import org.prgms.management.blacklist.vo.Blacklist;
import org.prgms.management.blacklist.repository.BlackListJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlacklistService {
    private final BlackListJdbcRepository blackListJdbcRepository;

    public BlacklistService(BlackListJdbcRepository blackListJdbcRepository) {
        this.blackListJdbcRepository = blackListJdbcRepository;
    }

    public Blacklist insert(Blacklist blacklist) {
        return blackListJdbcRepository.insert(blacklist);
    }

    public List<Blacklist> getAll() {
        return blackListJdbcRepository.findAll();
    }

    public Optional<Blacklist> getById(UUID blacklistId) {
        return blackListJdbcRepository.findById(blacklistId);
    }

    public Optional<Blacklist> getByCustomerId(UUID customerId) {
        return blackListJdbcRepository.findByCustomerId(customerId);
    }

    public Blacklist delete(Blacklist blacklist) {
        return blackListJdbcRepository.delete(blacklist);
    }

    public void deleteAll() {
        blackListJdbcRepository.deleteAll();
    }
}
