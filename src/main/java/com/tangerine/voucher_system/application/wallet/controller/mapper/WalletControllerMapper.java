package com.tangerine.voucher_system.application.wallet.controller.mapper;

import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.dto.UpdateWalletRequest;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WalletControllerMapper {

    WalletControllerMapper INSTANCE = Mappers.getMapper(WalletControllerMapper.class);

    @Mapping(target = "walletId", expression = "java(java.util.UUID.randomUUID())")
    WalletParam requestToParam(CreateWalletRequest request);

    WalletParam requestToParam(UpdateWalletRequest request);

    @Named("getDiscountValue")
    static double getDiscountValue(DiscountValue discountValue) {
        return discountValue.value();
    }

}
