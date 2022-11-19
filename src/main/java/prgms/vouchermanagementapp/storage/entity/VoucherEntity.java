package prgms.vouchermanagementapp.storage.entity;

public class VoucherEntity {

    private final String id;
    private final String type;
    private final String customerName;
    private final Long amount;
    private final Long ratio;

    public VoucherEntity(String id, String type, Long amount, Long ratio, String customerName) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.ratio = ratio;
        this.customerName = customerName;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
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
