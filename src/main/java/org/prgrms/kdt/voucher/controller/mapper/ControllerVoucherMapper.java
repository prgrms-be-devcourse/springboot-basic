package org.prgrms.kdt.voucher.controller.mapper;

import org.mapstruct.Mapper;
import org.prgrms.kdt.voucher.controller.dto.CreateVoucherApiRequest;
import org.prgrms.kdt.voucher.service.dto.CreateVoucherRequest;

@Mapper(componentModel = "spring")
public interface ControllerVoucherMapper {
    CreateVoucherRequest convertRequest(CreateVoucherApiRequest createVoucherApiRequest);
}
