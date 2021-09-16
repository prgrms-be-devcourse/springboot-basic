package org.prgrms.kdtspringorder.BlackCustomer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<BlacklistCustomer> getBlacklist() {
        return blacklistRepository.findAll();
    }
}
