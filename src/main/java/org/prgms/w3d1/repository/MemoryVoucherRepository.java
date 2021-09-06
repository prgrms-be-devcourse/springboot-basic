package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherType;
import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Profile("local")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final HashMap<UUID, Voucher> voucherHashMap = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherHashMap.get(voucherId));
    }

    @Override
    public void save(Voucher voucher) {
        voucherHashMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherHashMap.values());
    }

    // 이 아래에서 부턴 DB 에서 쓰는 영역

    @Override
    public Optional<Voucher> findByCustomerId(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public VoucherWallet findVoucherWallet(UUID customerId) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteVoucher(UUID voucherId) {

    }

    @Override
    public void deleteVoucher(UUID customerId, UUID voucherId) {

    }

    @Override
    public List<Voucher> findVouchersByType(VoucherType voucherType) {
        return null;
    }
}
