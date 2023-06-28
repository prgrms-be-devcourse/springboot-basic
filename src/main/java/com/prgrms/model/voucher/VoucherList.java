package com.prgrms.model.voucher;

import com.prgrms.model.dto.VoucherResponse;

import java.util.List;
import java.util.stream.Collectors;

public class VoucherList {
    private final int VOUCHER_MIN_SIZE = 0;
    private final String EmptyException = "등록된 바우처가 없습니다.";
    private List<Voucher> voucherList;

    public VoucherList(List<Voucher> voucherList) {
        validateVoucherListSize(voucherList);
        this.voucherList = voucherList;
    }

    private void validateVoucherListSize(List<Voucher> vouchers) {
        if (vouchers.size() == VOUCHER_MIN_SIZE) {
            throw new IllegalArgumentException(EmptyException);
        }
    }

    public List<VoucherResponse> convertVoucherResponse() {
        return this.voucherList.stream()
                .map(VoucherResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (VoucherResponse voucherResponse : convertVoucherResponse()) {
            System.out.println(voucherResponse.getVoucherPolicy().name() + " : "
                    + voucherResponse.getDiscountedValue()
                    + voucherResponse.getVoucherPolicy().getUnit());
        }
        return sb.toString();
    }
}
