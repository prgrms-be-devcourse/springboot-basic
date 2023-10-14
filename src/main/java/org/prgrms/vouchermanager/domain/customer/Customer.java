package org.prgrms.vouchermanager.domain.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
@RequiredArgsConstructor
public class Customer {
    private final UUID customerId;
    private final String name;
}
