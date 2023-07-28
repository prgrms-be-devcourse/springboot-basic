package com.tangerine.voucher_system.application.voucher.controller.mapper;

import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherParam;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoucherControllerMapper {

    VoucherControllerMapper INSTANCE = Mappers.getMapper(VoucherControllerMapper.class);

    @Mapping(target = "voucherId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "voucherType", target = "voucherType")
    @Mapping(source = "voucherType", target = "discountValue.voucherType")
    @Mapping(source = "discountValue", target = "discountValue.value")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
    VoucherParam requestToParam(CreateVoucherRequest request);

    @Mapping(source = "voucherType", target = "voucherType")
    @Mapping(source = "voucherType", target = "discountValue.voucherType")
    @Mapping(source = "discountValue", target = "discountValue.value")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
    VoucherParam requestToParam(UpdateVoucherRequest request);

    @Named("getDiscountValue")
    static double getDiscountValue(DiscountValue discountValue) {
        return discountValue.value();
    }

    @Mapping(source = "discountValue", target = "discountValue", qualifiedByName = "getDiscountValue")
    VoucherResponse resultToResponse(VoucherResult result);

    List<VoucherResponse> resultsToResponses(List<VoucherResult> voucherResults);
}
