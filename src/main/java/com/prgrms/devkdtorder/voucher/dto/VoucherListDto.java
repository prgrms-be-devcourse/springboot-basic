package com.prgrms.devkdtorder.voucher.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class VoucherListDto {
    private final List<VoucherDto> voucherList;
}
