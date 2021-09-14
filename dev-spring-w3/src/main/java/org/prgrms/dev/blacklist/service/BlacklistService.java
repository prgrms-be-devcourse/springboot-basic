package org.prgrms.dev.blacklist.service;

import org.prgrms.dev.blacklist.domain.Blacklist;
import org.prgrms.dev.blacklist.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<Blacklist> blackList() {
        return blacklistRepository.findAllInBlackList();
    }
}
