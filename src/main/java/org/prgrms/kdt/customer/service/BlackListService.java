package org.prgrms.kdt.customer.service;

import org.prgrms.kdt.customer.BlackList;
import org.prgrms.kdt.customer.repository.BlackListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListService {
    private final BlackListRepository blackListRepository;

    public BlackListService(final BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public List<BlackList> findAllBlacklist() {
        return blackListRepository.findAllBlacklist();
    }
}
