package org.prgrms.kdt.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Customer {

    public UUID getCustomerId();

    public String getName();

    public String getEmail();

    public LocalDateTime getLastLoginAt();

    public LocalDateTime getCreatedAt();

    String toString();

}
