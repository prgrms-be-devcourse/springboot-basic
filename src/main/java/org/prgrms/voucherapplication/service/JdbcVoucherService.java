package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.repository.voucher.jdbc.JdbcVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;
import java.util.Optional;

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
}
