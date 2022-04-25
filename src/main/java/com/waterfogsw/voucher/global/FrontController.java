package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.global.request.CreateRequest;
import com.waterfogsw.voucher.global.request.ListRequest;
import com.waterfogsw.voucher.global.request.RequestStrategy;
import com.waterfogsw.voucher.voucher.controller.VoucherController;
import org.springframework.stereotype.Component;

@Component
public class FrontController {

    private final MessageConverter converter;
    private final VoucherController controller;

    public FrontController(MessageConverter converter, VoucherController controller) {
        this.converter = converter;
        this.controller = controller;
    }

    public String request(PostRequest postRequest) {
        if (postRequest == null) {
            throw new IllegalArgumentException();
        }

        RequestStrategy requestStrategy;

        switch (postRequest.command()) {
            case CREATE -> {
                requestStrategy = new CreateRequest(converter, controller);
            }
            case LIST -> {
                requestStrategy = new ListRequest(converter, controller);
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }

        return requestStrategy.request(postRequest);
    }
}
