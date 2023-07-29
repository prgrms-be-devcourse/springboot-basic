package org.programmers.VoucherManagement.wallet.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmers.VoucherManagement.wallet.application.dto.WalletCreateResponse;
import org.programmers.VoucherManagement.wallet.domain.Wallet;


@Mapper(componentModel = "spring")
public interface WalletServiceMapper {
    WalletServiceMapper INSTANCE = Mappers.getMapper(WalletServiceMapper.class);

    @Mapping(source = "walletId", target = "walletId")
    @Mapping(source = "voucher.voucherId", target = "voucherId")
    @Mapping(source = "member.memberId", target = "memberId")
    WalletCreateResponse domainToCreateResponse(Wallet wallet);
}
