package org.prgrms.kdt.voucher.service.mapper;

import org.prgrms.kdt.global.Generator;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.dto.CreateVoucherRequest;
import org.springframework.stereotype.Component;


@Component
public class ServiceVoucherMapper {
    private final Generator generator;

    public ServiceVoucherMapper(Generator generator) {
        this.generator = generator;
    }

    public Voucher convertVoucher(CreateVoucherRequest request){
        VoucherType voucherType = request.voucherType();
        return new Voucher(generator.generateId(),
                voucherType,
                voucherType.createPolicy(request.discountAmount()),
                generator.generateTime());
    }
}
