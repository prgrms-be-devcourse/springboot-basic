package org.devcourse.voucher.controller.console.runner;

import org.devcourse.voucher.controller.console.VoucherController;
import org.devcourse.voucher.controller.console.dto.Response;
import org.devcourse.voucher.controller.console.dto.Status;
import org.devcourse.voucher.controller.console.dto.VoucherInfoResponse;
import org.devcourse.voucher.controller.console.dto.VoucherSaveRequest;
import org.devcourse.voucher.view.Input;

public class CreateCommandRunner implements CommandRunner {

    private final Input input;
    private final VoucherController controller;

    public CreateCommandRunner(Input input, VoucherController controller) {
        this.input = input;
        this.controller = controller;
    }

    @Override
    public Response run() {
        String type = input.getUserInput();
        String amount = input.getUserInput();
        VoucherSaveRequest request = new VoucherSaveRequest(type, amount);
        VoucherInfoResponse response = controller.createVoucher(request);

        return new Response(Status.RUNNING, response.convertToMessage());
    }

}
