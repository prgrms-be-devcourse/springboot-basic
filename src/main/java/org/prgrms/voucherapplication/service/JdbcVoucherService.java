package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.repository.customer.jdbc.JdbcCustomerRepository;
import org.prgrms.voucherapplication.repository.voucher.jdbc.JdbcVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JdbcVoucherService {

    private JdbcCustomerRepository customerRepository;
    private JdbcVoucherRepository voucherRepository;

    public JdbcVoucherService(JdbcCustomerRepository customerRepository, JdbcVoucherRepository voucherRepository) {
        this.customerRepository = customerRepository;
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

    public Optional<Customer> getCustomerByVoucherId(UUID voucherId) {
        Optional<SqlVoucher> voucher = voucherRepository.findById(voucherId);
        if (voucher.isPresent()) {
            return customerRepository.findById(voucher.get().getVoucherOwner());
        }
        return Optional.empty();
    }
}
