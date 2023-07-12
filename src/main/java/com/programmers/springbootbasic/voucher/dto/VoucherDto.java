package com.programmers.springbootbasic.voucher.dto;

import com.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.Optional;
import java.util.UUID;

public record VoucherDto(UUID id, String name, long value, VoucherType type, Optional<UUID> customerId) {

}
