package org.programmers.kdtspring.repository.voucher;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void save(Voucher voucher);

    Optional<Voucher> findById(Long voucherId);

    List<Voucher> findAll();

    List<Voucher> findByCustomer(Customer customer);

    Voucher updateCustomerId(Voucher voucher);

    void deleteOne(Voucher voucher);

    void deleteAll();

}
