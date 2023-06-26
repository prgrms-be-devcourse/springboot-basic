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

    private static final Logger log = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    private final BlacklistRepository blacklistRepository;
    private final Console console;

    public BlacklistService(BlacklistRepository blacklistRepository, Console console) {
        this.blacklistRepository = blacklistRepository;
        this.console = console;
    }

    public List<String> findAll() {
        List<String> blacklist = blacklistRepository.findAll();
        console.printBlacklist(blacklist);
        log.info("The blacklist has been printed.");

        return blacklist;
    }
}
