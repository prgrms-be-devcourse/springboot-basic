package com.devcourse.voucher.presentation;

import com.devcourse.global.console.Console;
import com.devcourse.voucher.application.VoucherService;
import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.presentation.dto.ApplicationRequest;
import com.devcourse.voucher.presentation.dto.ApplicationResponse;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherController implements ApplicationRunner {
    private static final String CREATED_RESPONSE = "\n:: Voucher Created ::";
    private static final String EXITING_RESPONSE = "\n:: Application Ended ::";

    private final Console console = new Console();
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean power = true;
        console.greeting();

        while (power) {
            ApplicationRequest request = console.readRequest();
            ApplicationResponse response = generateResponse(request);
            console.write(response.payload());
            power = response.power();
        }
    }

    public ApplicationResponse generateResponse(ApplicationRequest request) {
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
