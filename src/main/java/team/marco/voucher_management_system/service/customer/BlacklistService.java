package team.marco.voucher_management_system.service.customer;

import org.springframework.stereotype.Service;
import team.marco.voucher_management_system.controller.customer.dto.CustomerIdAndName;
import team.marco.voucher_management_system.repository.custromer.BlacklistRepository;

import java.util.List;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<CustomerIdAndName> getBlacklist() {
        return blacklistRepository.findAll();
    }
}
