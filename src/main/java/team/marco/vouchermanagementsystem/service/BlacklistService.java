package team.marco.vouchermanagementsystem.service;

import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.repository.custromer.CsvBlacklistRepository;
import team.marco.vouchermanagementsystem.repository.custromer.CustomerIdAndName;

import java.util.List;

@Service
public class BlacklistService {
    private final CsvBlacklistRepository blacklistRepository;

    public BlacklistService(CsvBlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<CustomerIdAndName> getBlacklist() {
        return blacklistRepository.findAll();
    }
}
