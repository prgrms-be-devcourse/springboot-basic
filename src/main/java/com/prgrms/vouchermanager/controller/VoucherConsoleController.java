package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.dto.VoucherRequest;
import com.prgrms.vouchermanager.dto.VoucherResponse;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanager.dto.VoucherRequest.*;
import static com.prgrms.vouchermanager.dto.VoucherResponse.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class VoucherConsoleController {
    private final VoucherService service;

    public Voucher create(VoucherCreateRequest request) {
        return service.create(request);
    }

    public VoucherAllListResponse findAll() {

        return new VoucherAllListResponse(service.findAll());
    }

    public Voucher findById(UUID id) {
        return service.findById(id);
    }

    public Voucher updateDiscount(UUID id, int discount) {
        return service.updateDiscount(id, discount);
    }

    public int delete(UUID id) {
        return service.delete(id);
    }
}
