package com.devcourse.voucher.presentation;

import com.devcourse.voucher.application.VoucherService;
import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.presentation.dto.ApplicationRequest;
import com.devcourse.voucher.presentation.dto.ApplicationResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherController {
    private static final String CREATED_RESPONSE = "\n:: Voucher Created ::";
    private static final String EXITING_RESPONSE = "\n:: Application Ended ::";

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public ApplicationResponse mapService(ApplicationRequest request) {
        return switch (request.command()) {
            case CREATE -> {
                voucherService.create((CreateVoucherRequest) request.payload());
                yield new ApplicationResponse<>(true, CREATED_RESPONSE);
            }
            case LIST -> {
                List<GetVoucherResponse> responses = voucherService.findAll();
                yield new ApplicationResponse<>(true, responses);
            }
            case EXIT -> new ApplicationResponse<>(false, EXITING_RESPONSE);
        };
    }
}
