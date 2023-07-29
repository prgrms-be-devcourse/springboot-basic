package com.tangerine.voucher_system.application.voucher.service.mapper;

import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherParam;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoucherServiceMapper {

    Voucher paramToDomain(VoucherParam voucherParam);

    VoucherResult domainToResult(Voucher domain);

    List<VoucherResult> domainsToResults(List<Voucher> domains);

}
