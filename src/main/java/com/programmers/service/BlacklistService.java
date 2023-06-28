package com.programmers.service;

import com.programmers.repository.BlacklistRepository;
import com.programmers.io.Console;
import com.programmers.repository.MemoryVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
