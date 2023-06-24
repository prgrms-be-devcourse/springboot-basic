package kr.co.programmers.springbootbasic.voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    List<Voucher> listAll();

    void save(UUID voucherId, Voucher voucher);
}
