package org.prgms.voucherProgram.service;

import java.util.List;

import org.prgms.voucherProgram.entity.user.User;
import org.prgms.voucherProgram.repository.user.FileUserRepository;
import org.prgms.voucherProgram.repository.user.UserRepository;

public class UserService {
    private final UserRepository fileUserRepository;

    public UserService(FileUserRepository fileUserRepository) {
        this.fileUserRepository = fileUserRepository;
    }

    public List<User> findBlackList() {
        return fileUserRepository.findAll();
    }
}
