package com.waterfogsw.voucher.global.request;

import com.waterfogsw.voucher.global.MessageConverter;
import com.waterfogsw.voucher.global.PostRequest;
import com.waterfogsw.voucher.global.Request;
import com.waterfogsw.voucher.voucher.controller.VoucherController;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;

public class CreateRequest extends RequestStrategy {

    private final MessageConverter messageConverter;

    public CreateRequest(MessageConverter messageConverter, VoucherController controller) {
        super(controller);
        this.messageConverter = messageConverter;
    }

    @Override
    public String request(Request request) {
        final var postRequest = (PostRequest) request;
        final var voucherDto = messageConverter.convert(postRequest);
        final var response = controller.voucherAdd(voucherDto);

        if (response.status() == ResponseStatus.BAD_REQUEST) {
            return ResponseStatus.BAD_REQUEST.name();
        }

        return response.body().toString();
    }
}
