package org.prgrms.kdt.wallet.controller.mapper;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.prgrms.kdt.wallet.controller.dto.CreateWalletApiRequest;
import org.prgrms.kdt.wallet.service.dto.CreateWalletRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-30T02:23:50+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ControllerWalletMapperImpl implements ControllerWalletMapper {

    @Override
    public CreateWalletRequest convertRequest(CreateWalletApiRequest request) {
        if ( request == null ) {
            return null;
        }

        UUID memberId = null;
        UUID voucherId = null;

        memberId = request.memberId();
        voucherId = request.voucherId();

        CreateWalletRequest createWalletRequest = new CreateWalletRequest( memberId, voucherId );

        return createWalletRequest;
    }
}
