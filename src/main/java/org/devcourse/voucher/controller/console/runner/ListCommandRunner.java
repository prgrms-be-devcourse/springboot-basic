package org.devcourse.voucher.controller.console.runner;

import org.devcourse.voucher.controller.console.VoucherController;
import org.devcourse.voucher.controller.console.dto.Response;
import org.devcourse.voucher.controller.console.dto.Status;
import org.devcourse.voucher.controller.console.dto.VoucherInfoResponse;

import java.util.List;

public class ListCommandRunner implements CommandRunner {

    private static final String MESSAGE_TEMPLATE = "Voucher ID : %d, TYPE : %s, AMOUNT : %d\n";
    private final VoucherController controller;

    public ListCommandRunner(VoucherController controller) {
        this.controller = controller;
    }

    @Override
    public Response run() {
        List<VoucherInfoResponse> responses = controller.listVoucher();

        return postProcessResponse(responses);
    }

    private Response postProcessResponse(List<VoucherInfoResponse> responses) {
        StringBuilder result = new StringBuilder();
        for (VoucherInfoResponse response : responses) {
            int id = response.id();
            String type = response.voucherType();
            int amount = response.amount();

            result.append(MESSAGE_TEMPLATE.formatted(id, type, amount));
        }

        return new Response(Status.RUNNING, result.toString());
    }
}
