package org.programmers.voucher;

import org.programmers.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Voucher assignToCustomer(Customer customer, Voucher voucher);

    int countAll();

    int countFixed();

    int countPercent();

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByOwnerId(UUID ownerId);

    void deleteAll();

    void deleteByCustomerId(Customer customerId);

}
