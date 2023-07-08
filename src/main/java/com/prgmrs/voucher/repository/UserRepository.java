package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository {
    void save(User user);
}
