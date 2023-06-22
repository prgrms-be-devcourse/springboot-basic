package com.programmers.voucher.stream;

import com.programmers.voucher.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Profile(value = "local")
public class MemoryVoucherStream implements VoucherStream {
    private final HashMap<String, Voucher> voucherList = new HashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherList.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Map<String, Voucher> findAll() {
        return voucherList;
    }
}
