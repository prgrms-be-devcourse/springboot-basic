package org.prgms.voucherProgram.service;

import java.util.List;

import org.prgms.voucherProgram.entity.user.User;
import org.prgms.voucherProgram.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findBlackList() {
        List<User> blackUsers = userRepository.findBlackUsers();
        logger.info("BlackUsers find at repository => {}", blackUsers.size());
        return blackUsers;
    }
}
