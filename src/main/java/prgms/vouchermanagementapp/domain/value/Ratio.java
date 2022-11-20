package prgms.vouchermanagementapp.domain.value;

import java.text.MessageFormat;
import java.util.Objects;

public class Ratio {

    private final long ratio;

    public Ratio(long ratio) {
        if (ratio < 0 || ratio > 100) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Discount Ratio ''{0}'' should between 0 and 100.", ratio)
            );
        }

        this.ratio = ratio;
    }

    public long getRatio() {
        return this.ratio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ratio otherRatio = (Ratio) o;
        return this.ratio == otherRatio.ratio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratio);
    }
}
