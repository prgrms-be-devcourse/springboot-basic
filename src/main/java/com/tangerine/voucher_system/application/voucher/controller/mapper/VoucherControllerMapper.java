package com.tangerine.voucher_system.application.voucher.controller.mapper;

import com.tangerine.voucher_system.application.global.generator.IdGenerator;
import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponses;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherParam;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class VoucherControllerMapper {

    private final IdGenerator generator;

    public VoucherControllerMapper(IdGenerator generator) {
        this.generator = generator;
    }

    public VoucherParam requestToParam(CreateVoucherRequest request) {
        return new VoucherParam(
                generator.getUuid(),
                request.voucherType(),
                new DiscountValue(request.voucherType(), request.discountValue()),
                LocalDate.now()
        );
    }

    public VoucherParam requestToParam(UpdateVoucherRequest request) {
        return new VoucherParam(
                request.voucherId(),
                request.voucherType(),
                new DiscountValue(request.voucherType(), request.discountValue()),
                LocalDate.now()
        );
    }

    public VoucherResponse resultToResponse(VoucherResult result) {
        return new VoucherResponse(
                result.voucherId(),
                result.voucherType(),
                result.discountValue().value(),
                result.createdAt()
        );
    }

    public VoucherResponses resultsToResponses(List<VoucherResult> voucherResults) {
        return new VoucherResponses(
                voucherResults.stream()
                        .map(this::resultToResponse)
                        .toList());
    }
}
