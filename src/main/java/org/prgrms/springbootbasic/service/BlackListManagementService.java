package org.prgrms.springbootbasic.service;

import org.prgrms.springbootbasic.entity.User;
import org.prgrms.springbootbasic.repository.BlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BlackListManagementService {
    private final BlackListRepository blackListRepository;

    @Autowired
    public BlackListManagementService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public List<User> lookupList() {
        return blackListRepository.findAll();
    }
}
