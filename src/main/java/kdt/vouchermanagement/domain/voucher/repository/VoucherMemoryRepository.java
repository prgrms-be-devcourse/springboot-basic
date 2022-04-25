package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Profile("memory")
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

    @Override
    public List<Voucher> findAll() {
        return storage.values()
                .stream()
                .collect(Collectors.toList());
    }

    private Voucher createVoucherEntity(VoucherType voucherType, int discountValue) {
        Long voucherId = VoucherIdGenerator.idGenerate();
        Voucher voucherEntity = voucherType.createEntity(voucherId, discountValue);
        return voucherEntity;
    }
}
