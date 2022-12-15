package prgms.vouchermanagementapp.model.dto;

import javax.validation.constraints.NotEmpty;

public class VoucherCreationDto {

    @NotEmpty
    private String voucherType;

    @NotEmpty
    private long discountLevel;

    public VoucherCreationDto() {
    }

    public VoucherCreationDto(String voucherType, long discountLevel) {
        this.voucherType = voucherType;
        this.discountLevel = discountLevel;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscountLevel() {
        return discountLevel;
    }
}

