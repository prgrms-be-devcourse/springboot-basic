package org.prgrms.springbootbasic.repository.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.customer.Customer;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();

    void removeAll();

    Voucher updateCustomerId(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByCustomer(Customer customer);

    void deleteVoucher(Voucher voucher);

    List<Voucher> findByType(VoucherType voucherType);

    List<Voucher> findByCreatedAt(LocalDateTime startTime, LocalDateTime endTime);
}
