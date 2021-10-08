package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    Voucher updateEmail(Voucher voucher, String email);

    Optional<List<Voucher>> findByEmail(String email);

    void delete(UUID voucherId);

    List<Customer> findCustomer(final UUID voucherId);
}
