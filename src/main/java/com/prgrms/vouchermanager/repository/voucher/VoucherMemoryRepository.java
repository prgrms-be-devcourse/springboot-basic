package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("dev")
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers = new HashMap<>();

    public Voucher create(Voucher voucher) {

        vouchers.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers
                .values()
                .stream()
                .toList();
    }

    @Override
    public Voucher findById(UUID id) {
        return vouchers.get(id);
    }

    @Override
    public Voucher updateDiscount(Voucher updateVoucher) {
        vouchers.computeIfPresent(updateVoucher.getId(), (k, v) -> updateVoucher);

        return updateVoucher;
    }

    @Override
    public int delete(UUID id) {
        vouchers.remove(id);
        return 1;
    }

    @Override
    public List<Voucher> findByDate(int startYear, int startMonth, int endYear, int endMonth) {
        return null;
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return null;
    }
}
