package com.example.voucher.service.voucher.dto;

import java.util.UUID;
import com.example.voucher.constant.VoucherType;

public record VoucherDTO(UUID voucherId, long value, VoucherType voucherType) {

}
