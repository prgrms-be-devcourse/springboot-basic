package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static com.prgrms.vouchermanager.dto.voucher.VoucherRequest.*;
import static com.prgrms.vouchermanager.dto.voucher.VoucherResponse.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class VoucherConsoleController {
    private final VoucherService service;

    public Voucher create(VoucherCreateRequest request) {
        return service.create(request);
    }

    public VoucherListResponse findAll() {

        return new VoucherListResponse(service.findAll());
    }

    public VoucherDetailResponse findById(UUID id) {
        return toDetailVoucher(service.findById(id));
    }

    public Voucher updateDiscount(UUID id, int discount) {
        return service.updateDiscount(id, discount);
    }

    public int delete(UUID id) {
        return service.delete(id);
    }
}
