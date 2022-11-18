package org.prgrms.springbootbasic.repository;


import org.prgrms.springbootbasic.entity.User;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

import java.util.List;
import java.util.Optional;


public interface BlackListRepository {

    void insert(User user);

    Optional<User> findById(long VoucherId);

    List<User> findAll();
}
