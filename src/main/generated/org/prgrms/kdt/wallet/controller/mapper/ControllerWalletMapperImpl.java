package org.prgrms.kdt.wallet.controller.mapper;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.prgrms.kdt.wallet.controller.dto.ControllerCreateWalletRequest;
import org.prgrms.kdt.wallet.service.dto.ServiceCreateWalletRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-20T18:31:51+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ControllerWalletMapperImpl implements ControllerWalletMapper {

    @Override
    public ServiceCreateWalletRequest controllerRequestToServiceRequest(ControllerCreateWalletRequest request) {
        if ( request == null ) {
            return null;
        }

        UUID walletId = null;
        UUID memberId = null;
        UUID voucherId = null;

        walletId = request.walletId();
        memberId = request.memberId();
        voucherId = request.voucherId();

        ServiceCreateWalletRequest serviceCreateWalletRequest = new ServiceCreateWalletRequest( walletId, memberId, voucherId );

        return serviceCreateWalletRequest;
    }
}
