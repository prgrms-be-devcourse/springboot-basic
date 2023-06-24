package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> vouchers = new LinkedHashMap<>();

    @Override
    public void saveVoucher(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
    }

}
