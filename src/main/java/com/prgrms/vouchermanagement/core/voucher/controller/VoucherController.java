package com.prgrms.vouchermanagement.core.voucher.controller;

import com.prgrms.vouchermanagement.core.voucher.controller.request.VoucherCreateRequest;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VoucherResponse;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VouchersResponse;
import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;
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

    /**
     * 바우처 등록
     * @param voucherCreateRequest
     */
    public void createVoucher(VoucherCreateRequest voucherCreateRequest) {
        VoucherDto voucherDto = new VoucherDto(voucherCreateRequest.getName(), voucherCreateRequest.getAmount(), VoucherType.getType(voucherCreateRequest.getVoucherType()));
        voucherService.create(voucherDto);
    }

    /**
     * 모든 바우처 조회
     * @return VouchersResponse
     */
    public VouchersResponse getAllVoucher() {
        List<VoucherDto> voucherDtoList = voucherService.findAll();
        return toVouchersResponse(voucherDtoList);
    }

    private static VouchersResponse toVouchersResponse(List<VoucherDto> voucherDtoList) {
        List<VoucherResponse> voucherResponseList = voucherDtoList.stream()
                .map(it -> new VoucherResponse(it.getId(), it.getName(), it.getAmount(), it.getVoucherType()))
                .collect(Collectors.toList());
        return new VouchersResponse(voucherResponseList);
    }
}
