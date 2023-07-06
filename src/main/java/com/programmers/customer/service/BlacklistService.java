package com.programmers.customer.service;

import com.programmers.customer.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistService {

    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<String> findAll() {
        return blacklistRepository.findAll();
    }
}
