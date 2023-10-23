package org.prgrms.vouchermanager.domain.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public class Customer {
    private final UUID customerId;
    private final String name;
    private final String email;
    private final Boolean isBlack;


}
