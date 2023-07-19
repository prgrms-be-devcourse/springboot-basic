package org.prgrms.kdt.wallet.controller.mapper;

import org.mapstruct.Mapper;
import org.prgrms.kdt.wallet.controller.dto.ControllerCreateWalletRequest;
import org.prgrms.kdt.wallet.service.dto.ServiceCreateWalletRequest;

@Mapper(componentModel = "spring")
public interface ControllerWalletMapper {
     ServiceCreateWalletRequest controllerRequestToServiceRequest(ControllerCreateWalletRequest request);
}
