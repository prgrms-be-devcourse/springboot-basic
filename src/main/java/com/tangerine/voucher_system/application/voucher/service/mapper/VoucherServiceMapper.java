package com.tangerine.voucher_system.application.voucher.service.mapper;

import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherParam;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherServiceMapper {

    public Voucher paramToDomain(VoucherParam voucherParam) {
        return new Voucher(
                voucherParam.voucherId(),
                voucherParam.voucherType(),
                voucherParam.discountValue(),
                voucherParam.createdAt()
        );
    }

    public VoucherResult domainToResult(Voucher domain) {
        return new VoucherResult(
                domain.voucherId(),
                domain.voucherType(),
                domain.discountValue(),
                domain.createdAt()
        );
    }

    public List<VoucherResult> domainsToResults(List<Voucher> domains) {
        return domains.stream()
                .map(this::domainToResult)
                .toList();
    }

}
