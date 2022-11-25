package org.prgrms.kdt.data;

public class VoucherDTO {

    private String voucherType;
    private Integer amount;

    public VoucherDTO() {
    }

    public VoucherDTO(String voucherType, Integer amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


}
