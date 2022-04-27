package com.example.voucherproject.voucher.dto;

import com.example.voucherproject.voucher.model.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public record VoucherDTO(
        VoucherType type, long amount
) {


}
