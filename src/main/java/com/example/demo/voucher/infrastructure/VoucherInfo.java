package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.application.VoucherType;
import com.example.demo.voucher.domain.Voucher;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class VoucherInfo {

    private final List<Voucher> voucherList;

    public VoucherInfo(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    public void add(String name, UUID id, long value) {
        voucherList.add(getType(name).createVoucher(id, value));
    }

    public void add(Voucher voucher) {
        voucherList.add(voucher);
    }

    private VoucherType getType(String name) {
        return VoucherType.valueOf(name);
    }

    public VoucherInfo getVouchers() {
        return new VoucherInfo(voucherList);
    }

    public List<Voucher> getVoucherList() {
        return Collections.unmodifiableList(voucherList);
    }

    public void clear() {
        voucherList.clear();
    }
}