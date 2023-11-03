package com.prgrms.vouchermanagement.core.voucher.repository.memory;

import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.core.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Profile("memory")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final List<Voucher> voucherArrayList = new CopyOnWriteArrayList<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherArrayList.add(voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherArrayList;
    }

    @Override
    public void deleteAll() {
        voucherArrayList.clear();
    }

    @Override
    public Optional<Voucher> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAllByIds(List<String> idList) {
        return null;
    }
}
