package com.tangerine.voucher_system.application.voucher.controller.mapper;

import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherParam;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VoucherControllerMapper {

    VoucherControllerMapper INSTANCE = Mappers.getMapper(VoucherControllerMapper.class);

    @Mapping(target = "voucherId", expression = "java(java.util.UUID.randomUUID())")
    VoucherParam requestToParam(CreateVoucherRequest request);

    VoucherParam requestToParam(UpdateVoucherRequest request);

    VoucherResponse resultToResponse(VoucherResult result);

}
