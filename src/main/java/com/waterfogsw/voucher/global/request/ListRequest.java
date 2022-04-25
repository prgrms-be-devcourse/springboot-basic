package com.waterfogsw.voucher.global.request;

import com.waterfogsw.voucher.global.MessageConverter;
import com.waterfogsw.voucher.global.Request;
import com.waterfogsw.voucher.voucher.controller.VoucherController;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;

import java.util.Objects;
import java.util.stream.Collectors;

public class ListRequest extends RequestStrategy {

    private final MessageConverter messageConverter;

    public ListRequest(MessageConverter messageConverter, VoucherController controller) {
        super(controller);
        this.messageConverter = messageConverter;
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
