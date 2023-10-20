package org.prgrms.vouchermanager.domain.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.UUID;
@RequiredArgsConstructor
@Getter
@ToString
public class Customer {
    private final String customerId;
    private final String name;


}
