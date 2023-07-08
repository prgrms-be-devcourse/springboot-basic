package org.weekly.weekly.voucher.dto;

import org.weekly.weekly.util.PrintMsg;
import org.weekly.weekly.voucher.domain.Voucher;

import java.util.List;
import java.util.stream.Collectors;

public class ListResponse implements Response{
    private final List<String> result;

    public ListResponse(List<Voucher> vouchers) {
        this.result = vouchers.stream()
                .map(Voucher::toString)
                .collect(Collectors.toUnmodifiableList());
    }

    public String getResult() {
        if (result.isEmpty()) {
            return PrintMsg.NO_VOUCHER_DATAS.getMsg();
        }

        StringBuilder sb = new StringBuilder();
        result.forEach(sb::append);
        return sb.toString();
    }
}
