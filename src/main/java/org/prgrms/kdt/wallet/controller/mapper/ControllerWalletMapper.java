package org.prgrms.kdt.wallet.controller.mapper;

import org.mapstruct.Mapper;
import org.prgrms.kdt.wallet.controller.dto.CreateWalletApiRequest;
import org.prgrms.kdt.wallet.service.dto.CreateWalletRequest;

@Mapper(componentModel = "spring")
public interface ControllerWalletMapper {
     CreateWalletRequest convertRequest(CreateWalletApiRequest request);
}
