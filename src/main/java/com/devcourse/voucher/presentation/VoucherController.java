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
    private static final String GREETING = """
            === Voucher Program ===
           Type <EXIT> to exit the program.
           Type <CREATE> to create a new voucher.
           Type <LIST> to list all vouchers.
           """;
    private static final String CREATION_RESPONSE = "\n:: Voucher Created ::";
    private static final String EXITING_RESPONSE = "\n:: Application Ended ::";

    private final Console console = new Console();
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean status = true;
        console.print(GREETING);

        while (status) {
            ApplicationRequest request = console.readRequest();
            ApplicationResponse response = toResponse(request.command(), request.payload());
            console.print(response.payload());
            status = response.status();
        }
    }

    public <T> ApplicationResponse toResponse(Command command, T payload) {
        return switch (command) {
            case CREATE -> {
                voucherService.create((CreateVoucherRequest) payload);
                yield new ApplicationResponse<>(true, CREATION_RESPONSE);
            }
            case LIST -> {
                List<GetVoucherResponse> responses = voucherService.findAll();
                yield new ApplicationResponse<>(true, responses);
            }
            case EXIT -> new ApplicationResponse<>(false, EXITING_RESPONSE);
        };
    }
}
