package com.tangerine.voucher_system.application.wallet.controller.mapper;

import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.dto.UpdateWalletRequest;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WalletControllerMapper {

    WalletControllerMapper INSTANCE = Mappers.getMapper(WalletControllerMapper.class);

    @Mapping(target = "walletId", expression = "java(java.util.UUID.randomUUID())")
    WalletParam requestToParam(CreateWalletRequest request);

    WalletParam requestToParam(UpdateWalletRequest request);

    VoucherResponse resultToResponse(VoucherResult result);

    CustomerResponse resultToResponse(CustomerResult result);

}
