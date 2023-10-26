package com.prgrms.vouchermanagement.core.voucher.controller;

import com.prgrms.vouchermanagement.core.voucher.controller.request.VoucherCreateRequest;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VoucherResponse;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VouchersResponse;
import com.prgrms.vouchermanagement.core.voucher.dto.VoucherDto;
import com.prgrms.vouchermanagement.core.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void createVoucher(VoucherCreateRequest voucherCreateRequest) {
        voucherService.createVoucher(voucherCreateRequest);
    }

    public VouchersResponse getAllVoucher() {
        List<VoucherDto> voucherDtoList = voucherService.findAllVoucher();
        List<VoucherResponse> voucherResponseList = voucherDtoList.stream()
                .map(it -> new VoucherResponse(it.getId(), it.getName(), it.getAmount(), it.getVoucherType()))
                .collect(Collectors.toList());
        return new VouchersResponse(voucherResponseList);
    }
}
