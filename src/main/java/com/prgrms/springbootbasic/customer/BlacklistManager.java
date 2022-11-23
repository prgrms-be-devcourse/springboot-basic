package com.prgrms.springbootbasic.customer;

import com.prgrms.springbootbasic.customer.storage.BlacklistStorage;
import com.prgrms.springbootbasic.customer.domain.Customer;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BlacklistManager {
    private final BlacklistStorage blacklistStorage;

    public BlacklistManager(BlacklistStorage blacklistStorage) {
        this.blacklistStorage = blacklistStorage;
    }

    public List<Customer> list() {
        return blacklistStorage.findAll();
    }
}
