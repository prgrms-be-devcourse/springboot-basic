package org.prgrms.kdt.blacklist;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class BlackListrService {

    private final UserRepository userRepository;

    public BlackListrService(@Qualifier("fileBlackListRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<BlackUser> list() throws IOException {
        return userRepository.findAll();
    }
}
