package com.waterfogsw.voucher.global.request;

import com.waterfogsw.voucher.global.Request;
import com.waterfogsw.voucher.voucher.controller.VoucherController;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;

import java.util.Objects;
import java.util.stream.Collectors;

public class ListRequest extends RequestStrategy {

    public ListRequest(VoucherController controller) {
        super(controller);
    }

    @Override
    public String request(Request request) {
        final var response = controller.voucherList();

        if (response.status() == ResponseStatus.BAD_REQUEST) {
            return ResponseStatus.BAD_REQUEST.name();
        }

        return response.body()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.joining("\n"));
    }
}
