package org.voucherProject.voucherProject.voucher.service;

import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher save(Voucher voucher);

    Voucher findById(UUID voucherId);

    List<Voucher> findByCustomer(Customer customer);

    List<Voucher> findAll();

    Voucher updateVoucher(Voucher voucher);

    void deleteOneVoucherByCustomer(Customer customer, Voucher voucher);
}
