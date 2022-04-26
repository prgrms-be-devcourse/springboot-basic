package org.prgrms.kdt.voucher.repository;

import java.util.List;
import org.prgrms.kdt.voucher.model.Voucher;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

}
