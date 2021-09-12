package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.domain.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Optional<Voucher> findById(Long voucherId);

    List<Voucher> findByType(VoucherType type);

    List<Voucher> findAll();

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher, Customer customer);

    void deleteAll();

    int count();
}
