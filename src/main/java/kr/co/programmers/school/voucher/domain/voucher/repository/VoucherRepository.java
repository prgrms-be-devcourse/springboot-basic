package kr.co.programmers.school.voucher.domain.voucher.repository;

import kr.co.programmers.school.voucher.domain.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class VoucherRepository {
    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();

    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }
}