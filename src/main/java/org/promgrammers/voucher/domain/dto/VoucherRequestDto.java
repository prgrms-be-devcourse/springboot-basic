package org.promgrammers.voucher.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.promgrammers.voucher.domain.VoucherType;



@RequiredArgsConstructor
@Getter
public class VoucherRequestDto {


    private final VoucherType voucherType;

    private final long amount;

}
