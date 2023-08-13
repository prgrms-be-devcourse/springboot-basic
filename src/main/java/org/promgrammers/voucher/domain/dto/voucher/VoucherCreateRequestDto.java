package org.promgrammers.voucher.domain.dto.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.promgrammers.voucher.domain.VoucherType;

import java.util.UUID;


@AllArgsConstructor
@Getter
@NoArgsConstructor
public class VoucherCreateRequestDto {

    private UUID id;

    private long amount;

    private VoucherType voucherType;


}
