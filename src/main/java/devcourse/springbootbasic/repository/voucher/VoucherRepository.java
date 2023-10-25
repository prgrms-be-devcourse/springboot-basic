package devcourse.springbootbasic.repository.voucher;


import devcourse.springbootbasic.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
