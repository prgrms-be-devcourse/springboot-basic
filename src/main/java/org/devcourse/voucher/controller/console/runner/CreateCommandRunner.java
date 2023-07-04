package org.devcourse.voucher.controller.console.runner;

import org.devcourse.voucher.controller.console.VoucherController;
import org.devcourse.voucher.controller.console.dto.Response;
import org.devcourse.voucher.controller.console.dto.Status;
import org.devcourse.voucher.controller.console.dto.VoucherInfoResponse;
import org.devcourse.voucher.controller.console.dto.VoucherSaveRequest;
import org.devcourse.voucher.domain.voucher.VoucherType;
import org.devcourse.voucher.view.Input;

public class CreateCommandRunner implements CommandRunner {

    private static final String MESSAGE_TEMPLATE = "Voucher ID : %d, TYPE : %s, AMOUNT : %d\n";
    private final Input input;
    private final VoucherController controller;

    public CreateCommandRunner(Input input, VoucherController controller) {
        this.input = input;
        this.controller = controller;
    }

    @Override
    public Response run() {
        VoucherSaveRequest request = createRequest();
        VoucherInfoResponse response = controller.createVoucher(request);

        return postProcessResponse(response);
    }

    private VoucherSaveRequest createRequest() {
        VoucherType voucherType = VoucherType.find(input.getUserInput());
        int inputAmount = Integer.parseInt(input.getUserInput());

        return new VoucherSaveRequest(voucherType, inputAmount);
    }

    private Response postProcessResponse(VoucherInfoResponse response) {
        long id = response.id();
        String type = response.voucherType();
        int amount = response.amount();

        return new Response(Status.RUNNING, MESSAGE_TEMPLATE.formatted(id, type, amount));
    }

}
