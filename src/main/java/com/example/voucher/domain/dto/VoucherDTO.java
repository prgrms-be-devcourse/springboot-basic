package com.example.voucher.domain.dto;

import java.util.UUID;
import com.example.voucher.constant.VoucherType;

public record VoucherDTO(UUID voucherID, long discountValue, VoucherType voucherType) {

}
