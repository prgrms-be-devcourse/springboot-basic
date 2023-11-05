package com.prgrms.vouchermanagement.core.voucher.controller;

import com.prgrms.vouchermanagement.core.voucher.controller.response.VoucherResponse;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VouchersResponse;
import com.prgrms.vouchermanagement.core.voucher.dto.VoucherDto;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static VouchersResponse toVouchersResponse(List<VoucherDto> voucherDtoList) {
        List<VoucherResponse> voucherResponseList = voucherDtoList.stream()
                .map(it -> new VoucherResponse(it.getId(), it.getName(), it.getAmount(), it.getVoucherType()))
                .collect(Collectors.toList());
        return new VouchersResponse(voucherResponseList);
    }
}
