package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.wrapper.Username;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    void save(User user);

    List<User> findAll();

    User findByUsername(Username username);

    List<User> getUserListWithVoucherAssigned();

    User getUserByVoucherId(UUID voucherId);
}
