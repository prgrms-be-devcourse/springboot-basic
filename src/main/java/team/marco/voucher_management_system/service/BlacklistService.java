package team.marco.voucher_management_system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import team.marco.voucher_management_system.console_app.repository.BlacklistRepository;
import team.marco.voucher_management_system.model.BlacklistUser;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<BlacklistUser> getBlacklist() {
        return blacklistRepository.findAll();
    }
}
