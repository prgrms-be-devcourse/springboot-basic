package com.prgrms.voucher_manager.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Customer {

    UUID getCustomerId();

    String getName();

    String getEmail();

    LocalDateTime getCreatedAt();

    LocalDateTime getLastLoginAt();

    void loginInNow();

}
