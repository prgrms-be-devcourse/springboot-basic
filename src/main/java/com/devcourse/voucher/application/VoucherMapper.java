package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherMapper {

    public Voucher mapFrom(CreateVoucherRequest request) {
        String symbol = request.typeSymbol();

        if (VoucherType.isFixed(symbol)) {
            return Voucher.fixed(request.discount(), request.expiredAt());
        }

        return Voucher.percent(request.discount(), request.expiredAt());
    }

    public List<GetVoucherResponse> toResponseList(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(this::toResponse)
                .toList();
    }

    private GetVoucherResponse toResponse(Voucher voucher) {
        return new GetVoucherResponse(voucher.getId(), voucher.getType(), voucher.getExpireAt(), voucher.getStatus());
    }
}
