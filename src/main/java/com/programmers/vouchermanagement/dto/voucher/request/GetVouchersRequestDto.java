package com.programmers.vouchermanagement.dto.voucher.request;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import lombok.Data;

import java.time.LocalDateTime;

//@Data
public record GetVouchersRequestDto(VoucherType type, LocalDateTime minCreatedAt, LocalDateTime maxCreatedAt) {
//    VoucherType type;
//    LocalDateTime minCreatedAt;
//    LocalDateTime maxCreatedAt;
}
