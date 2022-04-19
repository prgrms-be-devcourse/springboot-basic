package org.programmers.kdtspring.service;

import org.programmers.kdtspring.entity.user.BlackListedUser;
import org.programmers.kdtspring.entity.user.User;
import org.programmers.kdtspring.repository.user.BlackListUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlackListUserService {

    private final BlackListUserRepository blackListUserRepository;
    Logger logger = LoggerFactory.getLogger(BlackListUserService.class);

    public BlackListUserService(BlackListUserRepository blackListUserRepository) {
        this.blackListUserRepository = blackListUserRepository;
    }

    public void createBlackListUser(String userName) {

        logger.info("[BlackListUserService] createBlackListUser(String userName) called");

        BlackListedUser blackListedUser = new BlackListedUser(UUID.randomUUID(), userName);
        blackListUserRepository.saveBlackUser(blackListedUser);

        logger.info("{} is created and saved", blackListedUser);
    }


}
