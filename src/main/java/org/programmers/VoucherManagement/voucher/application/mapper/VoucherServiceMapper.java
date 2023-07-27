package org.programmers.VoucherManagement.voucher.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherCreateResponse;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherGetResponse;
import org.programmers.VoucherManagement.voucher.domain.Voucher;


@Mapper(componentModel = "spring")
public interface VoucherServiceMapper {
    VoucherServiceMapper INSTANCE = Mappers.getMapper(VoucherServiceMapper.class);

    @Mapping(source = "voucherId", target = "voucherId")
    @Mapping(source = "discountType", target = "discountType")
    @Mapping(source = "discountValue.value", target = "discountValue")
    VoucherCreateResponse domainToCreateResponse(Voucher voucher);

    @Mapping(source = "voucherId", target = "voucherId")
    @Mapping(source = "discountType", target = "discountType")
    @Mapping(source = "discountValue.value", target = "discountValue")
    VoucherGetResponse domainToGetResponse(Voucher voucher);

}
