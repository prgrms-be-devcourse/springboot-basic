package org.programmers.kdt.voucher.repository;

import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.voucher.Voucher;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    default void deleteVoucher(Voucher voucher) {
        deleteVoucher(voucher.getVoucherId());
    }
    void deleteVoucher(UUID voucherId);
    List<Voucher> findAll();
    List<Voucher> findAllUnregisteredVouchers();
    Voucher addOwner(Customer customer, Voucher voucher);
    void removeOwner(Customer customer, UUID voucherId);

    void releaseAllVoucherBelongsTo(Customer customer);

    Optional<UUID> findCustomerIdByVoucherId(UUID voucherId);
    List<Voucher> findVouchersByCustomerId(UUID customerId);
    List<Voucher> findVouchersBetween(Timestamp from, Timestamp to);
}
