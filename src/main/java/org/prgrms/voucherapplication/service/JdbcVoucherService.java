package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.repository.voucher.jdbc.JdbcVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JdbcVoucherService {

    private JdbcVoucherRepository voucherRepository;

    public JdbcVoucherService(JdbcVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void saveVoucher(SqlVoucher voucher) {
        voucherRepository.insert(voucher);
    }

    public Optional<List<SqlVoucher>> getVouchersByOwnedCustomer(Customer customer) {
        return voucherRepository.findByVoucherOwner(customer.getCustomerId());
    }

    public void deleteVouchersByOwnedCustomer(Customer customer) {
        Optional<List<SqlVoucher>> byVoucherOwner = voucherRepository.findByVoucherOwner(customer.getCustomerId());
        byVoucherOwner.ifPresent(vouchers ->
                vouchers.forEach(voucher -> voucherRepository.deleteById(voucher.getVoucherId())));
    }

    public Optional<List<SqlVoucher>> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public void issueVoucherToCustomer(UUID voucherId, Customer customer) {
        Optional<SqlVoucher> voucher = voucherRepository.findById(voucherId);
        if (voucher.isPresent()) {
            SqlVoucher sqlVoucher = voucher.get();
            sqlVoucher.issueVoucher(customer.getCustomerId());
            voucherRepository.update(sqlVoucher);
        }
    }
}
