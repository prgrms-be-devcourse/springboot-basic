package programmers.org.voucher.repository;

import programmers.org.voucher.domain.Voucher;

import java.util.*;

public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherStorage = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        voucherStorage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> getAll() {
        return new ArrayList<>(voucherStorage.values());
    }
}
