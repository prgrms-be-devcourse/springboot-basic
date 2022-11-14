package org.programmers.springbootbasic.dto;

import org.programmers.springbootbasic.data.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class VoucherDtoConverter {

    public VoucherDto convertVoucherInput(VoucherInputDto voucherInputDto) {
        return new VoucherDto(VoucherType.valueOfType(voucherInputDto.getType()), voucherInputDto.getAmount());
    }
}
