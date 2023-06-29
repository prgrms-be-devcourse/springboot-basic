package org.promgrammers.voucher.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@Getter
public class VoucherListResponseDto {

    private final List<VoucherResponseDto> voucherResponseDtoList;


}
