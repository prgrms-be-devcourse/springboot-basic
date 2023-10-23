package org.prgrms.vouchermanager.domain.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Customer {
    private final String customerId;
    private final String name;


}
