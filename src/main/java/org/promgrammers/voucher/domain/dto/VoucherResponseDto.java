package org.promgrammers.voucher.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.promgrammers.voucher.domain.VoucherType;

import java.util.UUID;


@RequiredArgsConstructor
@Getter
@ToString
public class VoucherResponseDto {

    private final UUID id;

    private final long amount;

    private final VoucherType voucherType;


}
