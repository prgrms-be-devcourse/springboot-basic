package org.promgrammers.voucher.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.promgrammers.voucher.domain.VoucherType;

import java.util.UUID;


@RequiredArgsConstructor
@Getter
public class VoucherResponseDto {

    private final UUID id;

    private final long amount;

    private final VoucherType voucherType;


}
