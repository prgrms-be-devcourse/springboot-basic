package org.weekly.weekly.voucher.dto.response;

import org.weekly.weekly.util.PrintMessageType;
import org.weekly.weekly.voucher.domain.Voucher;

import java.util.List;

public class VouchersResponse  {
    private final List<VoucherCreationResponse> result;

    public VouchersResponse(List<Voucher> vouchers) {
        this.result = vouchers.stream()
                .map(VoucherCreationResponse::new)
                .toList();
    }

    public List<VoucherCreationResponse> getResult() {
        return result;
    }

    public String result() {
        if (result.isEmpty()) {
            return PrintMessageType.NO_VOUCHER_DATAS.getMessage();
        }

        StringBuilder resultBuilder = new StringBuilder();
        result.forEach(voucherResponse-> resultBuilder.append(voucherResponse.result()).append('\n'));
        return resultBuilder.toString();
    }
}
