package org.weekly.weekly.voucher.model;

import org.weekly.weekly.voucher.domain.Voucher;

import java.util.List;
import java.util.stream.Collectors;

public class ListResponse {
    private final List<String> result;

    public ListResponse(List<Voucher> vouchers) {
        this.result = vouchers.stream()
                .map(Voucher::toString)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<String> getResult() {
        return result;
    }
}
