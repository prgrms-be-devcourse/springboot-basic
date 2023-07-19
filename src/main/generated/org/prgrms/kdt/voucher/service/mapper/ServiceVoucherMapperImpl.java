package org.prgrms.kdt.voucher.service.mapper;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.prgrms.kdt.voucher.domain.DiscountPolicy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.dto.ServiceCreateVoucherRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-20T02:49:14+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ServiceVoucherMapperImpl implements ServiceVoucherMapper {

    @Override
    public Voucher serviceDtoToVoucher(ServiceCreateVoucherRequest request) {
        if ( request == null ) {
            return null;
        }

        VoucherType voucherType = null;

        voucherType = request.voucherType();

        UUID voucherId = createUUID();
        DiscountPolicy discountPolicy = createDiscountPolicy(request.voucherType(), request.discountAmount());
        LocalDateTime createdAt = createLocalDateTime();

        Voucher voucher = new Voucher( voucherId, voucherType, discountPolicy, createdAt );

        return voucher;
    }
}
