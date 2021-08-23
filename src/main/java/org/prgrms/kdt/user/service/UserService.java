package org.prgrms.kdt.user.service;

import org.prgrms.kdt.user.domain.BannedCustomer;
import org.prgrms.kdt.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<BannedCustomer> getBlackList() {
        try {
            return userRepository.getBlackListCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
