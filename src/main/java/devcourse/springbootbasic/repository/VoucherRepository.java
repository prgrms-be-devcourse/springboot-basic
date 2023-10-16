package devcourse.springbootbasic.repository;


import devcourse.springbootbasic.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
