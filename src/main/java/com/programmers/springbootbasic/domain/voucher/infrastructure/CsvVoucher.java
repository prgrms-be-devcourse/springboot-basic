package com.programmers.springbootbasic.domain.voucher.infrastructure;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.UUID;

public class CsvVoucher {
    private UUID id;
    private String voucherType;
    private Integer benefitValue;


    public CsvVoucher() {
    }

    public CsvVoucher(UUID id, String voucherType, Integer benefitValue) {
        this.id = id;
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
    }

    public UUID getId() {
        return id;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public Integer getBenefitValue() {
        return benefitValue;
    }

    public static CsvVoucher of (Voucher voucher) {
        return new CsvVoucher(voucher.getId(), voucher.getVoucherType().getVoucherTypeName(), voucher.getBenefitValue());
    }

    public Voucher toEntity() {
        return new Voucher(id, VoucherTypeEnum.of(voucherType).getVoucherType(benefitValue), benefitValue);
    }
}
