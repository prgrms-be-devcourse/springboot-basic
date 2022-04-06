package org.prgrms.springbootbasic.repository;

import java.util.List;
import java.util.Optional;
import org.prgrms.springbootbasic.entity.Voucher;

public interface VoucherRepository {

    void save(Voucher voucher);

    Optional<List<Voucher>> findAll();

    Integer getVoucherTotalNumber();

    void removeAll();

}
