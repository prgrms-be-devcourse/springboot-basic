package org.prgrms.kdt.voucher.repository;

import java.util.Collection;
import org.prgrms.kdt.voucher.model.Voucher;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Collection<Voucher> getAll();

}
