package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
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

    @Override
    public List<Voucher> findAll() {

        return store.values()
                .stream()
                .toList();
    }

    private Voucher newVoucher(Voucher voucher){

        voucher = voucher.getVoucherType().createVoucher(
                IdGenerator.idGenerate(),
                voucher.getDiscountValue(),
                voucher.getVoucherType(),
                voucher.getCreatedAt(),
                voucher.getUpdatedAt()
        );

        return voucher;
    }
}
