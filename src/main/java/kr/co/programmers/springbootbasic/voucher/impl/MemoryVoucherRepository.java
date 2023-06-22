package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.voucher.Voucher;
import kr.co.programmers.springbootbasic.voucher.VoucherRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> repository;

    public MemoryVoucherRepository() {
        this.repository = new HashMap<>();
    }

    @Override
    public List<Voucher> listAll() {
        return repository.values()
                .stream()
                .toList();
    }

    @Override
    public Voucher save(Voucher voucher) {
        repository.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
