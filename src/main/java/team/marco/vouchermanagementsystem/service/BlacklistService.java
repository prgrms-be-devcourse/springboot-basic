package team.marco.vouchermanagementsystem.service;

import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.model.User;
import team.marco.vouchermanagementsystem.repository.BlacklistRepository;

import java.util.List;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<User> getBlacklist() {
        return blacklistRepository.findAll();
    }
}
