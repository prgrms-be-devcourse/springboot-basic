package org.prgrms.kdt.service;

import org.prgrms.kdt.repository.BlackListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListService {
    private BlackListRepository blackListRepository;

    public BlackListService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public List<String> getBlackList() {
        return blackListRepository.getBlackListByFile();
    }
}
