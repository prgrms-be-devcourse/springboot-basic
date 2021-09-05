package com.programmers.kdtspringorder.voucher.domain;

import com.programmers.kdtspringorder.voucher.exception.VoucherNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class VoucherWallet {
    private Map<UUID,Voucher> vouchers;

    public VoucherWallet() {}

    public VoucherWallet(Map<UUID, Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public List<Voucher> findAll(){
        return new ArrayList<>(vouchers.values());
    }

    public Voucher add(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    public Voucher remove(UUID voucherId) {
        Voucher removeVoucher = vouchers.remove(voucherId);
        if (removeVoucher == null) {
            throw new VoucherNotFoundException();
        }

        return removeVoucher;
    }
}
