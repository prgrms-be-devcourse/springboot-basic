package com.programmers.voucher.dto;

import com.programmers.voucher.domain.VoucherType;

import java.util.UUID;

public record VoucherDto(UUID id, String name, long value, VoucherType type) {

}
