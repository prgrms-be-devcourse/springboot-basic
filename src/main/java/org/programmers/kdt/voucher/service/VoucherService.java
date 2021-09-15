package org.programmers.kdt.voucher.service;

import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherStatus;
import org.programmers.kdt.voucher.VoucherType;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {
    Optional<Voucher> getVoucher(UUID voucherId);
    void useVoucher(Voucher voucher);
    Voucher createVoucher(VoucherType voucherType, UUID voucherId, long discount);
    default Voucher createdVoucher(VoucherType voucherType, long discount) {
        return createVoucher(voucherType, UUID.randomUUID(), discount);
    }
    default void removeVoucher(Voucher voucher) {
        removeVoucher(voucher.getVoucherId());
    }

    void removeVoucher(UUID voucherid);
    List<Voucher> getAllVouchers();
    List<Voucher> getAllUnregisteredVouchers();

    String getPrintFormat(Voucher voucher);
    VoucherStatus getVoucherStatus(Voucher voucher);

    Voucher addOwner(Customer customer, Voucher voucher);
    default void removeOwner(Customer customer, Voucher voucher) {
        removeOwner(customer, voucher.getVoucherId());
    }
    void removeOwner(Customer customer, UUID voucherId);

    Optional<UUID> findCustomerIdHoldingVoucherOf(UUID voucherId);
    default Optional<UUID> findCustomerIdHoldingVoucherOf(Voucher voucher) {
        return findCustomerIdHoldingVoucherOf(voucher.getVoucherId());
    }
    List<Voucher> getAllVouchersBelongsToCustomer(Customer customer);
    List<Voucher> getVouchersBetween(Timestamp from, Timestamp to);
}
