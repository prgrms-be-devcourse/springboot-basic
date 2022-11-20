package prgms.vouchermanagementapp.domain.value;

import java.text.MessageFormat;
import java.util.Objects;

public class Amount {

    private final long amount;

    public Amount(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Discount amount ''{0}'' cannot be negative: ", amount)
            );
        }
        this.amount = amount;
    }

    public long getAmount() {
        return this.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount otherAmount = (Amount) o;
        return this.amount == otherAmount.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
