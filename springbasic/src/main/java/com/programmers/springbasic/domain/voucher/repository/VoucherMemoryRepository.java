package com.programmers.springbasic.domain.voucher.repository;

import com.programmers.springbasic.domain.voucher.entity.Voucher;
import com.programmers.springbasic.domain.voucher.model.VoucherOption;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Profile("dev")
@Repository
public class VoucherMemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.getCode(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher> findAllByVoucherType(VoucherOption voucherOption) {
        switch (voucherOption) {
            case FIXED_AMOUNT_VOUCHER: {

                return vouchers.values()
                        .stream()
                        .filter(v -> v.getVoucherType().equals(VoucherOption.FIXED_AMOUNT_VOUCHER))
                        .collect(Collectors.toList());
            }
            case PERCENT_DISCOUNT_VOUCHER: {

                return vouchers.values()
                        .stream()
                        .filter(v -> v.getVoucherType().equals(VoucherOption.PERCENT_DISCOUNT_VOUCHER))
                        .collect(Collectors.toList());
            }
            default: {
                return Collections.emptyList();
            }
        }
    }
}
