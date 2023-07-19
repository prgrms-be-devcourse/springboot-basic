package com.tangerine.voucher_system.application.voucher.repository;

import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class VoucherMap {

    private final Map<UUID, Voucher> map;

    public VoucherMap() {
        this.map = new HashMap<>();
    }

    public VoucherMap(Map<UUID, Voucher> voucherMap) {
        this.map = voucherMap;
    }

    public Voucher addVoucher(Voucher voucher) {
        map.put(voucher.voucherId(), voucher);
        return voucher;
    }

    public Voucher addIfVoucherExist(Voucher voucher) {
        if (!map.containsKey(voucher.voucherId())) {
            throw new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText());
        }
        map.put(voucher.voucherId(), voucher);
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
