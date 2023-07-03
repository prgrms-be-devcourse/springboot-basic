package org.promgrammers.voucher.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.util.List;


@RequiredArgsConstructor
@Getter
public class VoucherListResponseDto {

    private final List<VoucherResponseDto> voucherResponseDtoList;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("바우처 리스트 : \n");
        for (VoucherResponseDto dto : voucherResponseDtoList) {
            sb.append(dto.toString()).append("\n");
        }
        return sb.toString();
    }


}
