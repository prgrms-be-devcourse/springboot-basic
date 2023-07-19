package com.tangerine.voucher_system.application.wallet.service.mapper;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import com.tangerine.voucher_system.application.wallet.model.Wallet;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WalletServiceMapper {

    WalletServiceMapper INSTANCE = Mappers.getMapper(WalletServiceMapper.class);

    Wallet paramToDomain(WalletParam param);

    WalletResult domainToResult(Wallet domain);

    VoucherResult domainToResult(Voucher domain);

    CustomerResult domainToResult(Customer domain);

}
