package prgms.spring_week1.domain.voucher.repository.impl;

import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherList = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        return voucherList.values()
                .stream()
                .toList();
    }

    @Override
    public void insert(Voucher voucher) {
        voucherList.put(voucher.getVoucherId(), voucher);
    }
}
