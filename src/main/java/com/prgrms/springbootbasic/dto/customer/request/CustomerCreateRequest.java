package com.prgrms.springbootbasic.dto.customer.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerCreateRequest {

    private String name;
    private String email;
    private LocalDateTime createAt;
}
