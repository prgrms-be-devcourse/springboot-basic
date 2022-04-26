package com.waterfogsw.voucher.global.request;

import com.waterfogsw.voucher.global.Request;
import com.waterfogsw.voucher.voucher.controller.VoucherController;
import org.springframework.stereotype.Component;

@Component
public abstract class RequestStrategy {
    protected final VoucherController controller;

    public RequestStrategy(VoucherController controller) {
        this.controller = controller;
    }

    public abstract String request(Request request);
}
