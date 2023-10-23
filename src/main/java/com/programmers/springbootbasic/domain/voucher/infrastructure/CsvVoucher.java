package com.programmers.springbootbasic.domain.voucher.infrastructure;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.UUID;

public class CsvVoucher {

    private String id;
    private String voucherType;
    private String benefitValue;

    public CsvVoucher() {
    }

    public CsvVoucher(String id, String voucherType, String benefitValue) {
        this.id = id;
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
    }

    public static CsvVoucher of(Voucher voucher) {
        return new CsvVoucher(voucher.getId().toString(),
            voucher.getVoucherType().getVoucherTypeName(),
            voucher.getBenefitValue().toString());
    }

    public Voucher toEntity() {
        return new Voucher(UUID.fromString(id),
            VoucherTypeEnum.of(voucherType).getVoucherType(Integer.valueOf(benefitValue)),
            Integer.valueOf(benefitValue));
    }

    public String getId() {
        return id;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public String getBenefitValue() {
        return benefitValue;
    }
}
