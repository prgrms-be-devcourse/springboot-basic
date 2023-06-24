package prgms.spring_week1.domain.voucher.repository.impl;

import org.springframework.stereotype.Repository;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID,Voucher> voucherList = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        return voucherList.values().stream().toList();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        voucherList.put(voucher.getVoucherId(),voucher);
        return voucher;
    }
}
