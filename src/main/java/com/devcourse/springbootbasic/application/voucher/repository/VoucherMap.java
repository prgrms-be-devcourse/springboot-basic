package com.devcourse.springbootbasic.application.voucher.repository;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VoucherMap {

    private final Map<UUID, Voucher> map;

    public VoucherMap() {
        this.map = new HashMap<>();
    }

    public VoucherMap(Map<UUID, Voucher> voucherMap) {
        this.map = voucherMap;
    }

    public Voucher addVoucher(Voucher voucher) {
        map.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    public Voucher addIfVoucherExist(Voucher voucher) {
        if (!map.containsKey(voucher.getVoucherId())) {
            throw new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText());
        }
        map.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    public List<Voucher> getAllVouchers() {
        return map.values()
                .stream()
                .toList();
    }

    public Voucher getVoucherById(UUID voucherId) {
        return map.get(voucherId);
    }

    public void clearVoucherMap() {
        map.clear();
    }

    public void removeVoucherById(UUID voucherId) {
        map.remove(voucherId);
    }
}
