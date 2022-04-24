package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    private static final Map<Long, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException("애플리케이션이 null을 사용하려고 합니다.");
        }

        if (voucher.getVoucherId() == null) {
            Voucher voucherEntity = createVoucherEntity(voucher.getVoucherType(), voucher.getDiscountValue());
            storage.put(voucherEntity.getVoucherId(), voucherEntity);
            return voucherEntity;
        }

        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    private Voucher createVoucherEntity(VoucherType voucherType, int discountValue) {
        Long voucherId = VoucherIdGenerator.idGenerate();
        Voucher voucherEntity = voucherType.createEntity(voucherId, discountValue);
        return voucherEntity;
    }
}
