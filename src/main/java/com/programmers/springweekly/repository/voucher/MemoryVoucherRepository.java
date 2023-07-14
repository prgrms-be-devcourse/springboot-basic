package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.Voucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
@Slf4j
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public void update(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Voucher voucher = voucherMap.get(voucherId);
            return Optional.of(voucher);
        } catch (NullPointerException e) {
            log.warn("바우처 ID로 바우처를 찾을 수 없어서 예외 발생, Optional Empty로 반환 {}", voucherId, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public int deleteById(UUID voucherId) {
        try {
            voucherMap.remove(voucherId);
            return 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void deleteAll() {
        voucherMap.clear();
        log.warn("주의, voucher 메모리 저장소에 있는 데이터 모두 삭제처리 됨.");
    }

    @Override
    public boolean existById(UUID voucherId) {
        try {
            voucherMap.get(voucherId);
            return true;
        } catch (NullPointerException e) {
            log.warn("[existById] 바우처 ID가 메모리 저장소에 없어서 예외 발생 {}", voucherId, e);
            return false;
        }
    }

}
