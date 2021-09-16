package org.prgrms.kdt.web.dto.voucher;


public class RequestCreateVoucherDto {

    private String voucherType;
    private Long voucherValue;

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public Long getVoucherValue() {
        return voucherValue;
    }

    public void setVoucherValue(Long voucherValue) {
        this.voucherValue = voucherValue;
    }
}
