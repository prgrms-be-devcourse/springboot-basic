package org.prgrms.devcourse.blackuser;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class BlackUserService {
    private final BlackUserRepository blackUserRepository;

    public BlackUserService(BlackUserRepository blackUserRepository) {
        this.blackUserRepository = blackUserRepository;
    }

    public BlackUser getBlackUser(String name) {
        return blackUserRepository
                .findByName(name)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a black user for {0}", name)));
    }

    public List<BlackUser> getBlackUserList() {
        return blackUserRepository.findAll();
    }
}
