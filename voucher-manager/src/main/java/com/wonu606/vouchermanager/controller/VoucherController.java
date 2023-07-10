package com.wonu606.vouchermanager.controller;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import com.wonu606.vouchermanager.service.voucher.VoucherService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

    private final VoucherService service;

    public VoucherController(VoucherService service) {
        this.service = service;
    }

    public Voucher createVoucher(VoucherDto voucherDto) {
        return service.createVoucher(voucherDto);
    }

    public List<Voucher> getVoucherList() {
        return service.getVoucherList();
    }
}
