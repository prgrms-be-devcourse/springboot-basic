package kr.co.programmers.school.voucher.domain.voucher.dto;

import kr.co.programmers.school.voucher.domain.voucher.domain.Voucher;

import java.util.List;

public class VoucherListResponse {
    private final List<Voucher> voucherList;

    public VoucherListResponse(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    public static VoucherListResponse from(List<Voucher> voucherList) {
        return new VoucherListResponse(voucherList);
    }

    public List<String> getVoucherList() {
        return voucherList.stream()
                .map(Voucher::printVoucher)
                .toList();
    }
}