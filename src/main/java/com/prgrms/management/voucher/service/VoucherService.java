package com.prgrms.management.voucher.service;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherRequest voucherRequest) {
        Voucher voucher = new Voucher(voucherRequest);
        return voucherRepository.save(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public void createVoucherByCustomerId(UUID voucherId, UUID customerId) {
        voucherRepository.updateVoucherByCustomerId(voucherId,customerId);
    }

    public void deleteVoucherByCustomerId(UUID customerId) {
        voucherRepository.deleteById(customerId);
    }

    public List<UUID> findCustomersByVoucherType(VoucherType voucherType) {
        return voucherRepository.findCustomerIdByVoucherType(voucherType);
    }
}
