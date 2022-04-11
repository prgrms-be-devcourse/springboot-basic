package org.prgrms.kdt.blacklist.service;

import org.prgrms.kdt.blacklist.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public BlacklistRepository getBlacklistRepository() {
        return blacklistRepository;
    }

}
