package com.example.voucher.domain.dto;

import java.util.UUID;
import com.example.voucher.domain.Voucher;

public record VoucherDTO(UUID voucherID, long discountValue, Voucher.Type voucherType) {

}
