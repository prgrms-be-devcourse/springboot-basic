package com.prgrms.springbootbasic.dto.voucher.request;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherCreateRequest {

    private long discount;
    private VoucherType type;
    private LocalDateTime createAt;
}