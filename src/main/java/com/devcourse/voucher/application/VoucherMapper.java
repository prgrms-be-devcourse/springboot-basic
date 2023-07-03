package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {

    public Voucher toEntity(CreateVoucherRequest request) {
        String symbol = request.typeSymbol();

        if (VoucherType.isFixType(symbol)) {
            return Voucher.fixed(request.discount(), request.expiredAt());
        }

        return Voucher.percent(request.discount(), request.expiredAt());
    }

    public GetVoucherResponse toResponse(Voucher voucher) {
        return new GetVoucherResponse(voucher.getId(),
                voucher.getType(),
                voucher.getDiscount(),
                voucher.getExpireAt(),
                voucher.getStatus());
    }
}
