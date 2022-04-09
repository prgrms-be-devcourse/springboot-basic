package org.prgrms.vouchermanagement.service;

import org.prgrms.vouchermanagement.domain.Voucher;
import org.prgrms.vouchermanagement.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher findVoucher(UUID voucherId) {

        return voucherRepository.findById(voucherId)
                .orElseThrow(() ->  new RuntimeException("blahblah"));
    }

    @Override
    public List<Voucher> findVouchers() {

        return voucherRepository.findAll();
    }

    @Override
    public UUID create(Long discountValue) {

        return null;
    }

}
