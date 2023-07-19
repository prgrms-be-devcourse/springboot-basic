package org.prgrms.kdt.voucher.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.prgrms.kdt.voucher.domain.DiscountPolicy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.dto.ServiceCreateVoucherRequest;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ServiceVoucherMapper {
    @Mapping(target = "voucherId", expression = "java(createUUID())")
    @Mapping(source = "voucherType", target = "voucherType")
    @Mapping(target = "discountPolicy", expression = "java(createDiscountPolicy(request.voucherType(), request.discountAmount()))")
    Voucher serviceDtoToVoucher(ServiceCreateVoucherRequest request);

    default UUID createUUID(){
        return UUID.randomUUID();
    }

    default DiscountPolicy createDiscountPolicy(VoucherType voucherType, double discountAmount){
        return voucherType.createPolicy(discountAmount);
    }
}
