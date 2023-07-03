package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {

    public Voucher toEntity(CreateVoucherRequest request) {
        VoucherType voucherType = request.type();

        if (voucherType.isPercent()) {
            return Voucher.percent(request.discount(), request.expiredAt());
        }

        return Voucher.fixed(request.discount(), request.expiredAt());
    }

    public GetVoucherResponse toResponse(Voucher voucher) {
        return new GetVoucherResponse(voucher.getId(),
                voucher.getType(),
                voucher.getDiscount(),
                voucher.getExpireAt(),
                voucher.getStatus());
    }
}
