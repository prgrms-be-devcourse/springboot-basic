package com.pgms.part1.domain.voucher.dto;

import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;

public record VoucherWalletResponseDtos(Long id, Integer discount, VoucherDiscountType voucherDiscountType, Long walletId) {
}

