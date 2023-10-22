package org.prgrms.vouchermanager.domain.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerRequestDto {
    private final String name;
    private final String email;
    private final Boolean isBlack;
}
