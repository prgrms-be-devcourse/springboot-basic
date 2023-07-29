package org.programmers.VoucherManagement.wallet.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmers.VoucherManagement.wallet.application.dto.WalletCreateRequest;
import org.programmers.VoucherManagement.wallet.application.dto.WalletCreateResponse;
import org.programmers.VoucherManagement.wallet.presentation.dto.WalletCreateRequestData;
import org.programmers.VoucherManagement.wallet.presentation.dto.WalletCreateResponseData;

@Mapper(componentModel = "spring")
public interface WalletControllerMapper {
    WalletControllerMapper INSTANCE = Mappers.getMapper(WalletControllerMapper.class);

    @Mapping(source = "voucherId", target = "voucherId")
    @Mapping(source = "memberId", target = "memberId")
    WalletCreateRequest dataToCreateRequest(WalletCreateRequestData data);

    @Mapping(source = "walletId", target = "walletId")
    @Mapping(source = "voucherId", target = "voucherId")
    @Mapping(source = "memberId", target = "memberId")
    WalletCreateResponseData createResponseToData(WalletCreateResponse response);
}
