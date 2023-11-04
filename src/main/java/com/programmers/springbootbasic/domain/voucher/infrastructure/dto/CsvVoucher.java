package com.programmers.springbootbasic.domain.voucher.infrastructure.dto;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.util.SqlConverter;
import java.util.UUID;

public class CsvVoucher {

    private String id;
    private String voucherType;
    private String benefitValue;
    private String createdAt;

    public CsvVoucher() {
    }

    public CsvVoucher(String id, String voucherType, String benefitValue, String createdAt) {
        this.id = id;
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
        this.createdAt = createdAt;
    }

    public static CsvVoucher of(Voucher voucher) {
        return new CsvVoucher(voucher.getId().toString(),
            voucher.getVoucherType().getVoucherTypeName(),
            voucher.getBenefitValue().toString(),
            voucher.getCreatedAt().toString()
        );
    }

    public Voucher toEntity() {
        return new Voucher(UUID.fromString(id),
            VoucherTypeEnum.of(voucherType).getVoucherType(Integer.valueOf(benefitValue)),
            Integer.valueOf(benefitValue),
            SqlConverter.toLocalDateTime(createdAt));
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
