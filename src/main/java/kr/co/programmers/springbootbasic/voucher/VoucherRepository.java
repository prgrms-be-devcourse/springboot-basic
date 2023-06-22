package kr.co.programmers.springbootbasic.voucher;

import java.util.List;

public interface VoucherRepository {
    List<Voucher> listAll();

    Voucher save(Voucher voucher);
}
