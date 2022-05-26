package org.programmers.springbootbasic.application.voucher.controller;

import org.programmers.springbootbasic.application.voucher.controller.api.CreateVoucherRequest;
import org.programmers.springbootbasic.application.voucher.model.Voucher;
import org.programmers.springbootbasic.application.voucher.controller.api.VoucherResponse;
import org.programmers.springbootbasic.application.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class VoucherConverter {
    public Voucher convertVoucher(CreateVoucherRequest createVoucherRequestDto) {
        var voucherType = VoucherType.findByType(createVoucherRequestDto.voucherType());

        return voucherType.create(UUID.randomUUID(), createVoucherRequestDto.value(), LocalDateTime.now());
    }
    public VoucherResponse convertVoucherDto (Voucher voucher) {
        return new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getValue(),
                voucher.getCreatedAt(),
                voucher.getVoucherType());
    }
}
