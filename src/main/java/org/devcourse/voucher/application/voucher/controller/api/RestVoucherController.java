package org.devcourse.voucher.application.voucher.controller.api;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.devcourse.voucher.application.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voucher")
public class RestVoucherController implements VoucherController {

    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    @PostMapping("")
    public Voucher postCreateVoucher(@RequestBody VoucherRequest voucherRequest) {
        return voucherService.createVoucher(voucherRequest.getVoucherType(), voucherRequest.getPrice());
    }

    @Override
    @GetMapping("")
    public List<Voucher> getVoucherList() {
        return voucherService.recallAllVoucher();
    }
}
