package kr.co.programmers.springbootbasic.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);
    Optional<Voucher> findByVoucherId(UUID voucherId);
    List<Voucher> listAll();
}
