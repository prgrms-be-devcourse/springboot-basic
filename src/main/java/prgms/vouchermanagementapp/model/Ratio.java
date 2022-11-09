package prgms.vouchermanagementapp.model;

public class Ratio {

    private final long ratio;

    public Ratio(long ratio) throws IllegalArgumentException {
        if (ratio < 0 || ratio > 100) {
            throw new IllegalArgumentException("Discount Ratio should between 0 and 100: " + ratio);
        }

        this.ratio = ratio;
    }

    public long getRatio() {
        return this.ratio;
    }
}
