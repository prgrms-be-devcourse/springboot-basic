package org.prgrms.vouchermanagement.customer.domain;

import java.util.UUID;

public interface Customer {
    UUID getCustomerId();
    String getName();
}
