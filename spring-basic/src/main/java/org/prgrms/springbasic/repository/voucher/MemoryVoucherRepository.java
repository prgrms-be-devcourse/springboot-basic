package org.prgrms.springbasic.repository.voucher;

import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.prgrms.springbasic.domain.wallet.Wallet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Profile("local")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        //JDBC만 구현
        return null;
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return null;
    }

    @Override
    public List<Voucher> findByCreatedPeriod(String from, String to) {
        return null;
    }

    @Override
    public List<Voucher> findVouchers() {
        return storage.values().stream().toList();
    }

    @Override
    public List<Wallet> findWallets() {
        //JDBC만 구현
        return null;
    }

    @Override
    public int countVouchers() {
        return storage.size();
    }

    @Override
    public Voucher update(Voucher voucher) {
        //JDBC만 구현
        return null;
    }

    @Override
    public boolean deleteByVoucherId(UUID voucherId) {
        //JDBC만 구현
        return true;
    }

    @Override
    public boolean deleteByCustomerId(UUID customerId) {
        //JDBC만 구현
        return true;
    }

    @Override
    public void deleteVouchers() {
        storage.clear();
    }
}
