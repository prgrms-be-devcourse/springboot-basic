package org.prgrms.kdt.voucher.controller.mapper;

import org.mapstruct.Mapper;
import org.prgrms.kdt.voucher.controller.dto.ControllerCreateVoucherRequest;
import org.prgrms.kdt.voucher.service.dto.ServiceCreateVoucherRequest;

@Mapper(componentModel = "spring")
public interface ControllerVoucherMapper {
    ServiceCreateVoucherRequest controllerDtoToServiceDto(ControllerCreateVoucherRequest controllerCreateVoucherRequest);
}
