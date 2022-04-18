package com.prgrms.management.voucher.service;

import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherRequest voucherRequest) {
        return null;
        //return voucherRepository.insert(VoucherType.of(voucherRequest.getVoucherType()).createVoucher(voucherRequest.getAmount()));
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public void createVoucherByCustomerId(UUID voucherId, UUID customerId) {
        Voucher voucher = voucherRepository.findById(voucherId);
        voucher.setCustomerId(customerId);
        voucherRepository.save(voucher);
    }

    public void deleteVoucherByCustomerId(UUID customerId) {
        voucherRepository.deleteByCustomerId(customerId);
    }

    public void findCustomersByVoucherType(VoucherType voucherType) {
        voucherRepository.findCustomerIdByVoucherType(voucherType);
    }
}
