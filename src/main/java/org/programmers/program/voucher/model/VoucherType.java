package org.programmers.program.voucher.model;

import java.util.Map;
import java.util.Optional;

public enum VoucherType {
    FIXED(1L, 100000L),
    PERCENT(1L, 100L);
    private final Long lowerBound;
    private final Long upperBound;
    VoucherType(Long lowerBound, Long upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    private static final Map<String, VoucherType> map = Map.of(
            "1", FIXED,
            "2", PERCENT
    );

    public static Optional<VoucherType> getVoucherType(String selection){
        if(map.containsKey(selection))
            return Optional.of(map.get(selection));
        return Optional.empty();
    }

    public String getRange(){
        return this.lowerBound + " ~ " + this.upperBound + " ";
    }
    public Long getLowerBound(){
        return lowerBound;
    }
    public Long getUpperBound(){
        return upperBound;
    }
}
