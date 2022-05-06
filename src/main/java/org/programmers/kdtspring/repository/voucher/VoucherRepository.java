package org.programmers.kdtspring.repository.voucher;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void insert(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    List<Voucher> findByCustomer(Customer customer);

    void updateCustomerId(Voucher voucher);

    void deleteOne(Voucher voucher);

    void deleteAll();

    List<Voucher> findByType(String voucherType);
}