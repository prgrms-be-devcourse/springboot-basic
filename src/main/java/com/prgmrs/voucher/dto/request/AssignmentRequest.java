package com.prgmrs.voucher.dto.request;

import com.prgmrs.voucher.model.Voucher;

import java.util.List;

public class AssignmentRequest {
    private final String username;
    private final String order;
    private final List<Voucher> voucherList;


    public AssignmentRequest(String username, String order, List<Voucher> voucherList) {
        this.username = username;
        this.order = order;
        this.voucherList = voucherList;
    }

    public String getUsername() {
        return username;
    }

    public String getOrder() {
        return order;
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }
}
