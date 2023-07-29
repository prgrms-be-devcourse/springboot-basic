package org.programmers.VoucherManagement.voucher.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherCreateRequest;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherCreateResponse;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherUpdateRequest;
import org.programmers.VoucherManagement.voucher.presentation.dto.VoucherCreateRequestData;
import org.programmers.VoucherManagement.voucher.presentation.dto.VoucherCreateResponseData;
import org.programmers.VoucherManagement.voucher.presentation.dto.VoucherUpdateRequestData;


@Mapper(componentModel = "spring")
public interface VoucherControllerMapper {

    VoucherControllerMapper INSTANCE = Mappers.getMapper(VoucherControllerMapper.class);

    @Mapping(source = "discountType", target = "discountType")
    @Mapping(source = "discountValue", target = "discountValue")
    VoucherCreateRequest dataToCreateRequest(VoucherCreateRequestData data);

    @Mapping(source = "discountValue", target = "discountValue")
    VoucherUpdateRequest dataToUpdateRequest(VoucherUpdateRequestData data);

    @Mapping(source = "voucherId", target = "voucherId")
    @Mapping(source = "discountType", target = "discountType")
    @Mapping(source = "discountValue", target = "discountValue")
    VoucherCreateResponseData createResponseToData(VoucherCreateResponse response);
}
