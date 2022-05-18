package org.devcourse.voucher.voucher.controller.api;

import org.devcourse.voucher.voucher.controller.dto.CreateVoucherRequest;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestVoucherController implements VoucherController {

    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    @PostMapping("/api/v1/voucher")
    public Voucher postCreateVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        return voucherService.createVoucher(createVoucherRequest.getVoucherType(), createVoucherRequest.getPrice());
    }

    @Override
    @GetMapping("/api/v1/voucher")
    public List<Voucher> getVoucherList() {
        return voucherService.recallAllVoucher();
    }
}
