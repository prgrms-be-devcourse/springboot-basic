package org.programmer.kdtspringboot.user;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final BlackListUserRepository blackListUserRepository;


    public UserService(BlackListUserRepository blackListUserRepository) {
        this.blackListUserRepository = blackListUserRepository;
    }

    public void createBlackListUser(UUID userId, String userName) {
        BlackListUser blackListUser = new BlackListUser(userId, userName);
        blackListUserRepository.saveUser(blackListUser);
    }
}
