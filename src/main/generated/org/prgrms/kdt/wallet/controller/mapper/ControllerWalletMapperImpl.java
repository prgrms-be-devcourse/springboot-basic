package org.prgrms.kdt.wallet.controller.mapper;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.prgrms.kdt.wallet.controller.dto.CreateWalletControllerRequest;
import org.prgrms.kdt.wallet.service.dto.CreateWalletServiceRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-22T15:51:04+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ControllerWalletMapperImpl implements ControllerWalletMapper {

    @Override
    public CreateWalletServiceRequest controllerRequestToServiceRequest(CreateWalletControllerRequest request) {
        if ( request == null ) {
            return null;
        }

        UUID walletId = null;
        UUID memberId = null;
        UUID voucherId = null;

        walletId = request.walletId();
        memberId = request.memberId();
        voucherId = request.voucherId();

        CreateWalletServiceRequest createWalletServiceRequest = new CreateWalletServiceRequest( walletId, memberId, voucherId );

        return createWalletServiceRequest;
    }
}
