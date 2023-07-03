package com.devcourse.voucher;

import com.devcourse.console.Console;
import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.presentation.Command;
import com.devcourse.voucher.presentation.VoucherController;
import com.devcourse.voucher.presentation.dto.ApplicationRequest;
import com.devcourse.voucher.presentation.dto.ApplicationResponse;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplicationRunner implements ApplicationRunner {
    private final VoucherController voucherController;
    private final Console console = new Console();

    public VoucherApplicationRunner(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean power = true;
        console.greeting();

        while (power) {
            ApplicationRequest request = initRequest();
            ApplicationResponse response = voucherController.mapService(request);
            console.write(response.payload());
            power = response.power();
        }
    }

    private ApplicationRequest initRequest() {
        String readCommand = console.readCommand();
        Command command = Command.from(readCommand);

        if (command.isCreation()) {
            CreateVoucherRequest request = console.readCreationRequest();
            return ApplicationRequest.creation(request);
        }

        return ApplicationRequest.noPayload(command);
    }
}
