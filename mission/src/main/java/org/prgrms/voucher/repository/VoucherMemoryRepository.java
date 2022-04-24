package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    private static final Map<Long, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {

        if (voucher == null) {
            throw new IllegalArgumentException("Voucher is null");
        }

        if (voucher.getVoucherId() == null) {
            voucher = newVoucher(voucher);
        }

        store.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    private Voucher newVoucher(Voucher voucher){

        voucher = voucher.getVoucherType().createVoucher(
                IdGenerator.idGenerate(),
                voucher.getDiscountValue(),
                voucher.getVoucherType()
        );

        return voucher;
    }
}
