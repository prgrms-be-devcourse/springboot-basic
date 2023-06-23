package prgms.spring_week1.domain.voucher.repository.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.io.Output;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID,Voucher> voucherList = new ConcurrentHashMap<>();

    @Override
    public Optional<List<Voucher>> findAll() {
        return Optional.ofNullable(voucherList.values().stream().toList());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        voucherList.put(voucher.getVoucherId(),voucher);
        return voucher;
    }
}
