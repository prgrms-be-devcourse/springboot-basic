package org.prgrms.springorder.domain.voucher.api;

import org.prgrms.springorder.console.io.Request;
import org.prgrms.springorder.console.io.Response;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.api.request.VoucherIdRequest;
import org.prgrms.springorder.global.exception.IllegalRequestException;
import org.springframework.stereotype.Component;

@Component
public class VoucherControllerDecorator {

    private final VoucherController voucherController;

    public VoucherControllerDecorator(
        VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    public Response createVoucher(Request request) {
        if (request instanceof VoucherCreateRequest) {
            return voucherController.createVoucher((VoucherCreateRequest) request);
        }
        throw new IllegalRequestException(VoucherCreateRequest.class, request.getClass());
    }

    public Response findAllVoucher() {
        return voucherController.findAllVoucher();
    }

    public Response findCustomerWithVoucher(Request request) {

        if (request instanceof VoucherIdRequest) {
            return voucherController.findCustomerWithVoucher((VoucherIdRequest) request);
        }

        throw new IllegalRequestException(VoucherIdRequest.class, request.getClass());
    }

}
