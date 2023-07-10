package com.prgmrs.voucher.dto.request;

import com.prgmrs.voucher.model.Voucher;

import java.util.List;

public class UserListRequest {
    private final String order;
    private final List<Voucher> voucherList;


    public UserListRequest(String order, List<Voucher> voucherList) {
        this.order = order;
        this.voucherList = voucherList;
    }

    public String getOrder() {
        return order;
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }
}
