package org.devcourse.voucher.controller.console.runner;

import org.devcourse.voucher.controller.console.VoucherController;
import org.devcourse.voucher.controller.console.dto.Response;
import org.devcourse.voucher.controller.console.dto.Status;
import org.devcourse.voucher.controller.console.dto.VoucherInfoResponse;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class ListCommandRunner implements CommandRunner {

    private final VoucherController controller;

    public ListCommandRunner(VoucherController controller) {
        this.controller = controller;
    }

    @Override
    public Response run() {
        List<VoucherInfoResponse> responses = controller.listVoucher();

        return new Response(Status.RUNNING, convertToMessage(responses));
    }

    private String convertToMessage(List<VoucherInfoResponse> responses) {
        return responses.stream()
                .map(VoucherInfoResponse::convertToMessage)
                .collect(joining("\n"));
    }
}
