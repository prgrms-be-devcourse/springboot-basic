package org.promgrammers.voucher.domain.dto.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.promgrammers.voucher.domain.VoucherType;

import java.util.UUID;


@AllArgsConstructor
@Getter
@ToString
public class VoucherResponseDto {

    private UUID id;

    private long amount;

    private VoucherType voucherType;


}
