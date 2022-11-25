package org.prgrms.springorder.domain.voucher.api;

import java.util.List;
import org.prgrms.springorder.console.io.ListResponse;
import org.prgrms.springorder.console.io.Response;
import org.prgrms.springorder.console.io.StringResponse;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.prgrms.springorder.domain.voucher_wallet.service.VoucherWalletService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private final VoucherWalletService voucherWalletService;

    public VoucherController(VoucherService voucherService,
        VoucherWalletService voucherWalletService) {
        this.voucherService = voucherService;
        this.voucherWalletService = voucherWalletService;
    }

    public Response createVoucher(VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = voucherService.createVoucher(voucherCreateRequest);

        return new StringResponse(voucher);
    }

    public Response findAllVoucher() {
        List<Voucher> vouchers = voucherService.findAll();
        return new ListResponse(vouchers);
    }

    public Response findCustomerWithVoucher(VoucherIdRequest voucherIdRequest) {
        CustomerWithVoucher customerWithVoucher = voucherWalletService.findCustomerWithVoucherByVoucherId(
            voucherIdRequest.getVoucherId());
        return new StringResponse(customerWithVoucher);
    }

}
