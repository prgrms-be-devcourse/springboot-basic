package org.prgrms.kdt.voucher.controller.mapper;

import javax.annotation.processing.Generated;
import org.prgrms.kdt.voucher.controller.dto.ControllerCreateVoucherRequest;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.dto.ServiceCreateVoucherRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-19T22:27:07+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ControllerVoucherMapperImpl implements ControllerVoucherMapper {

    @Override
    public ServiceCreateVoucherRequest controllerDtoToServiceDto(ControllerCreateVoucherRequest controllerCreateVoucherRequest) {
        if ( controllerCreateVoucherRequest == null ) {
            return null;
        }

        VoucherType voucherType = null;
        double discountAmount = 0.0d;

        voucherType = controllerCreateVoucherRequest.voucherType();
        discountAmount = controllerCreateVoucherRequest.discountAmount();

        ServiceCreateVoucherRequest serviceCreateVoucherRequest = new ServiceCreateVoucherRequest( voucherType, discountAmount );

        return serviceCreateVoucherRequest;
    }
}
