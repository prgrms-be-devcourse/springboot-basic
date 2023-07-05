package org.devcourse.voucher.controller.console;

import org.devcourse.voucher.app.VoucherService;
import org.devcourse.voucher.controller.console.dto.VoucherInfoResponse;
import org.devcourse.voucher.controller.console.dto.VoucherSaveRequest;
import org.devcourse.voucher.domain.voucher.Voucher;
import org.devcourse.voucher.domain.voucher.vo.VoucherVO;

import java.util.List;

public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherInfoResponse createVoucher(VoucherSaveRequest request) {
        Voucher voucher = new Voucher(0, request.voucherType(), request.amount());
        Voucher savedVoucher = voucherService.save(voucher);

        VoucherVO value = savedVoucher.values();

        return new VoucherInfoResponse(value);
    }

    public List<VoucherInfoResponse> listVoucher() {
        List<Voucher> vouchers = voucherService.findAll();

        return vouchers.stream()
                .map(Voucher::values)
                .map(VoucherInfoResponse::new)
                .toList();
    }
}
