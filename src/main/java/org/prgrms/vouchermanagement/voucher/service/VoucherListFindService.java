package org.prgrms.vouchermanagement.voucher.service;

import org.prgrms.vouchermanagement.exception.voucher.VoucherNotFoundException;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherListFindService {

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherListFindService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }

    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return voucherRepository.findVouchersByCustomerId(customerId);
    }

    public Voucher findVoucherByVoucherId(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(VoucherNotFoundException::new);
    }
}
