package prgms.vouchermanagementapp.storage.entity;

public class VoucherEntity {

    private final String uuid;
    private final String voucherType;
    private final String customerName;
    private final Long amount;
    private final Long ratio;

    public VoucherEntity(String uuid, String voucherType, Long amount, Long ratio, String customerName) {
        this.uuid = uuid;
        this.voucherType = voucherType;
        this.amount = amount;
        this.ratio = ratio;
        this.customerName = customerName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getRatio() {
        return ratio;
    }

    public String getCustomerName() {
        return customerName;
    }
}
