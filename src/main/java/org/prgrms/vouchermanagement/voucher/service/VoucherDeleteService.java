package org.prgrms.vouchermanagement.voucher.service;

import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherDeleteService {

    private final VoucherRepository voucherRepository;

    public VoucherDeleteService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void deleteVouchersByCustomerId(UUID customerId) {
        voucherRepository.deleteVoucherByCustomerId(customerId);
    }
}
