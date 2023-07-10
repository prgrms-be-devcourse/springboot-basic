package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;

import java.util.List;

public interface UserRepository {
    void save(User user);

    List<User> findAll();

    User findByUsername(String username);

    List<User> getUserListWithVoucherAssigned();

    User getUserByVoucherId(Voucher voucher);
}
