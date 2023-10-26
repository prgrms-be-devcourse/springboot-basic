package team.marco.vouchermanagementsystem.service;

import java.util.List;
import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.model.User;
import team.marco.vouchermanagementsystem.repository.BlacklistRepository;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<String> getBlacklist() {
        return blacklistRepository.findAll()
                .stream()
                .map(User::getInfo)
                .toList();
    }
}
