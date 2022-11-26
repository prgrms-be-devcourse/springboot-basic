package org.prgrms.kdt.controller;

import org.prgrms.kdt.controller.request.CreateVoucherRequest;
import org.prgrms.kdt.controller.response.VoucherResponse;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.dto.CreateVoucherDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public boolean createVoucher(CreateVoucherRequest createVoucherRequest) {
        CreateVoucherDto createVoucherDto = new CreateVoucherDto(createVoucherRequest.getVoucherType(), createVoucherRequest.getDiscountAmount());
        return voucherService.createVoucher(createVoucherDto);
    }

    public List<VoucherResponse> getAllVouchers() {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        return vouchers.stream().map(VoucherResponse::new).toList();
    }
}
