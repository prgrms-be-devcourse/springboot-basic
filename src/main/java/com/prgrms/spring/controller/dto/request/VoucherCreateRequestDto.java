package com.prgrms.spring.controller.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
public class VoucherCreateRequestDto {
    private String voucherType;
    private Long discount;
}
