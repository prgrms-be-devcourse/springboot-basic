package org.programmers.kdtspring.service;

import org.programmers.kdtspring.entity.user.BlackListedUser;
import org.programmers.kdtspring.repository.user.BlackListUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BlackListUserService {

    private static final Logger logger = LoggerFactory.getLogger(BlackListUserService.class);

    private final BlackListUserRepository blackListUserRepository;

    public BlackListUserService(BlackListUserRepository blackListUserRepository) {
        this.blackListUserRepository = blackListUserRepository;
    }

    public void createBlackListUser(String userName) {

        logger.info("[BlackListUserService] createBlackListUser(String userName) called");

        BlackListedUser blackListedUser = new BlackListedUser(UUID.randomUUID(), userName);
        blackListUserRepository.saveBlackUser(blackListedUser);

        logger.info("{} is created and saved", blackListedUser);
    }

    public List<String[]> showBlackList() {
        return blackListUserRepository.findAll();
    }
}