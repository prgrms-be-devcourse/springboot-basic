package org.prgrms.kdt.voucher;

public class VoucherMetaData {

    private String type;
    private String amount;

    public VoucherMetaData(String type, String amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }
}
