package team.marco.vouchermanagementsystem.service;

import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.model.Customer;
import team.marco.vouchermanagementsystem.repository.CsvBlacklistRepository;

import java.util.List;

@Service
public class BlacklistService {
    private final CsvBlacklistRepository blacklistRepository;

    public BlacklistService(CsvBlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<Customer> getBlacklist() {
        return blacklistRepository.findAll();
    }
}
