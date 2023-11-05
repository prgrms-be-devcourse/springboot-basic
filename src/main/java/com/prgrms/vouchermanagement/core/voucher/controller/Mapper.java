package com.prgrms.vouchermanagement.core.voucher.controller;

import com.prgrms.vouchermanagement.core.voucher.controller.request.VoucherCreateRequest;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VoucherCreateResponse;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VoucherDeleteResponse;
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

    public static VoucherCreateResponse toVoucherCreationResponse(VoucherDto voucherDto) {
        return new VoucherCreateResponse(voucherDto.getId(), voucherDto.getName(), voucherDto.getAmount(), voucherDto.getVoucherType().toString());
    }

    public static VoucherDto toVoucherDto(VoucherCreateRequest voucherCreateRequest) {
        return new VoucherDto(voucherCreateRequest.getName(), voucherCreateRequest.getAmount(), voucherCreateRequest.getVoucherType());
    }

    public static VoucherResponse toVoucherResponse(VoucherDto voucherDto) {
        return new VoucherResponse(voucherDto.getId(),voucherDto.getName(), voucherDto.getAmount(), voucherDto.getVoucherType());
    }

    public static VoucherDeleteResponse toVoucherDeleteResponse(String id) {
        return new VoucherDeleteResponse(id);
    }
}
