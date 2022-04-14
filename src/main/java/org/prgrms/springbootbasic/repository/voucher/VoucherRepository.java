package org.prgrms.springbootbasic.repository.voucher;

import java.util.List;
import org.prgrms.springbootbasic.entity.Voucher;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();

    void removeAll();

}
