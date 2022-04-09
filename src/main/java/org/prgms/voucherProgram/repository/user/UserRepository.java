package org.prgms.voucherProgram.repository.user;

import java.util.List;

import org.prgms.voucherProgram.entity.user.User;

public interface UserRepository {
    List<User> findAll();
}
