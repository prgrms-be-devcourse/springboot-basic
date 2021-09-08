package org.prgms.w3d1.service;

import org.prgms.w3d1.model.blacklist.Blacklist;
import org.prgms.w3d1.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BlacklistService {
    private BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public Blacklist getBlacklist(UUID blacklistId){
        return blacklistRepository
                .findById(blacklistId)
                .orElseThrow(() -> new RuntimeException("해당 id의 블랙리스트를 찾을 수 없습니다. " + blacklistId));
    }

    public void save(UUID blacklistId, String name){
        blacklistRepository.save(new Blacklist(blacklistId, name));
    }

    public List<Blacklist> findAll() {
        return blacklistRepository.findAll();
    }
}
