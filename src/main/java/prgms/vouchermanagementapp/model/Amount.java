package prgms.vouchermanagementapp.model;

public class Amount {

    private final long amount;

    public Amount(long amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Discount amount cannot be negative: " + amount);
        }

        this.amount = amount;
    }

    public long getAmount() {
        return this.amount;
    }
}
