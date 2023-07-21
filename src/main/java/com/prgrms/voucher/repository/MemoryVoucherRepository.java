package com.prgrms.voucher.repository;

import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.Vouchers;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Component
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<Integer, Voucher> storage = new TreeMap<>();

    @Override
    public Optional<Voucher> findById(int voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public int deleteById(int voucherId) {
        storage.remove(voucherId);
        return voucherId;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    public Vouchers getAllVoucher(VoucherType voucherType, LocalDateTime createdAt) {
        return new Vouchers(storage.values()
                .stream()
                .filter(voucher -> {
                    boolean typeMatch = containsType(voucherType, voucher.getVoucherType());
                    boolean createdAtMatch = isCreatedLaterThan(createdAt, voucher.getCreatedAt());

                    return typeMatch && createdAtMatch;
                })
                .toList());
    }

    private boolean containsType(VoucherType voucherType, VoucherType targetVoucherType) {
        if (voucherType == null) {
            return true;
        }
        return voucherType.equals(targetVoucherType);
    }

    private boolean isCreatedLaterThan(LocalDateTime createdAt, LocalDateTime targetCreatedAt) {
        if (createdAt == null) {
            return true;
        }
        return createdAt.compareTo(targetCreatedAt) <= 0;
    }
}
