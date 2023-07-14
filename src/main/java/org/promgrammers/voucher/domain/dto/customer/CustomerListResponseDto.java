package org.promgrammers.voucher.domain.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.promgrammers.voucher.domain.dto.voucher.VoucherResponseDto;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class CustomerListResponseDto {
    private List<CustomerResponseDto> CustomerResponseDtoList;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("바우처 리스트 : \n");
        for (CustomerResponseDto dto : CustomerResponseDtoList) {
            sb.append(dto.toString()).append("\n");
        }
        return sb.toString();
    }
}
