package org.promgrammers.voucher.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.util.List;


@AllArgsConstructor
@Getter
public class VoucherListResponseDto {

    private List<VoucherResponseDto> voucherResponseDtoList;


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
