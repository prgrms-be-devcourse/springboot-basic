package org.prgrms.kdt.wallet.controller.mapper;

import org.mapstruct.Mapper;
import org.prgrms.kdt.wallet.controller.dto.CreateWalletControllerRequest;
import org.prgrms.kdt.wallet.service.dto.CreateWalletServiceRequest;

@Mapper(componentModel = "spring")
public interface ControllerWalletMapper {
     CreateWalletServiceRequest controllerRequestToServiceRequest(CreateWalletControllerRequest request);
}
