package org.prgrms.springorder.domain.customer.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerCreateRequest {

    private final String name;

    private final String email;

}
