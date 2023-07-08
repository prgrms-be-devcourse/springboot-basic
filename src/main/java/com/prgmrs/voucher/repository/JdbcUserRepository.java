package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.User;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserRepository implements UserRepository {
    @Override
    public void save(User user) {

    }
}
