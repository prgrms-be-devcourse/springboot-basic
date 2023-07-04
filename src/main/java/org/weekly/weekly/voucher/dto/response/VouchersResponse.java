package org.weekly.weekly.voucher.dto.response;

import org.weekly.weekly.util.PrintMessage;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.Response;

import java.util.List;
import java.util.stream.Collectors;

public class VouchersResponse implements Response {
    private final List<VoucherCreationResponse> result;

    public VouchersResponse(List<Voucher> vouchers) {
        this.result = vouchers.stream()
                .map(VoucherCreationResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public String getResult() {
        if (result.isEmpty()) {
            return PrintMessage.NO_VOUCHER_DATAS.getMessage();
        }


        StringBuilder resultBuilder = new StringBuilder();
        result.forEach(voucherResponse-> resultBuilder.append(voucherResponse.getResult()).append('\n'));
        return resultBuilder.toString();
    }
}
