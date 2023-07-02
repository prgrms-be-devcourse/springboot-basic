package com.wonu606.vouchermanager.controller;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherDto;
import com.wonu606.vouchermanager.service.VoucherService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoucherController {

    private final VoucherService service;

    public Voucher createVoucher(VoucherDto voucherDto) {
        return service.createVoucher(voucherDto);
    }

    public List<Voucher> getVoucherList() {
        return service.getVoucherList();
    }
}
