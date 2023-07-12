package com.devcourse.springbootbasic.application.voucher.repository;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Voucher> getAllVouchersByCustomerId(UUID customerId) {
        return map.values().stream()
                .filter(voucher -> voucher.getCustomerId() == customerId)
                .toList();
    }

    public void removeVoucherByCustomerIdAndVoucherId(UUID customerId, UUID voucherId) {
        var list = map.values().stream()
                .filter(voucher -> voucher.getVoucherId() != voucherId || voucher.getCustomerId() != customerId)
                .toList();
        map.clear();
        list.forEach(voucher -> map.put(voucher.getVoucherId(), voucher));
    }

    public Optional<Voucher> getVoucherByCustomerIdAndVoucherId(UUID customerId, UUID voucherId) {
        return map.values().stream()
                .filter(voucher -> voucher.getVoucherId() == voucherId && voucher.getCustomerId() == customerId)
                .findAny();
    }
}
