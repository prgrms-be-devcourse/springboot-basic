package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.controller.dto.CreateVoucherApiRequest;
import org.prgrms.kdt.voucher.controller.mapper.ControllerVoucherMapper;
import org.prgrms.kdt.voucher.service.dto.VoucherResponses;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {
    private final VoucherService voucherService;
    private final ControllerVoucherMapper mapper;

    public VoucherController(VoucherService voucherService, ControllerVoucherMapper mapper) {
        this.voucherService = voucherService;
        this.mapper = mapper;
    }

    public void create(CreateVoucherApiRequest request) {
        voucherService.createVoucher(mapper.controllerDtoToServiceDto(request));
    }

    public VoucherResponses findAll() {
        return voucherService.findAll();
    }
}
