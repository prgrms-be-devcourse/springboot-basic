package org.voucherProject.voucherProject.voucher.service;

import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;

import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher save(Voucher voucher);

    Voucher findById(UUID voucherId);

    List<Voucher> findByCustomer(Customer customer);

    List<Voucher> findByVoucherType(VoucherType voucherType);

    List<Voucher> findByCreatedAtBetween(String date1, String date2);

    List<Voucher> findAll();

    Voucher updateVoucher(Voucher voucher);

    void deleteOneVoucherByCustomer(UUID customerId, UUID voucherId);

    void useVoucher(Voucher voucher);

    void cancelVoucher(Voucher voucher);
}
