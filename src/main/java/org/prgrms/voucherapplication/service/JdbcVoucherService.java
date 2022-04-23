package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.repository.voucher.jdbc.JdbcVoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class JdbcVoucherService {

    private JdbcVoucherRepository voucherRepository;

    public JdbcVoucherService(JdbcVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void saveVoucher(SqlVoucher voucher) {
        voucherRepository.insert(voucher);
    }
}
