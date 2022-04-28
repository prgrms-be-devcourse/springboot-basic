package org.prgrms.kdt.controller;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherRestController {
    private final VoucherService voucherService;


    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    @ResponseBody
    public Map<UUID, Voucher> getVoucherList() {
        VoucherMap voucherList = voucherService.getVoucherList();
        return voucherList.getVouchers();
    }

    @GetMapping("/api/v1/voucher/id/{voucherId}")
    @ResponseBody
    public Optional<Voucher> getVoucherByID(@PathVariable String voucherId) {
        return voucherService.getVoucherById(UUID.fromString(voucherId));
    }
}
