package com.program.commandLine.service;

import com.program.commandLine.repository.VoucherRepository;
import com.program.commandLine.voucher.Voucher;
import com.program.commandLine.voucher.VoucherFactory;
import com.program.commandLine.voucher.VoucherType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public Voucher createVoucher(String voucherTypeNumber, UUID voucherId, int discount) {
        VoucherType voucherType = VoucherType.getType(voucherTypeNumber);
        Voucher newVoucher = voucherFactory.createVoucher(voucherType, voucherId, discount);
        return voucherRepository.insertVoucher(newVoucher);
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public Voucher assignCustomer(Voucher voucher, UUID customerId) {
        voucher.assignCustomer(customerId);
        return voucher;
    }

    public List<Voucher> getAssignedVouchersByCustomer(UUID customerId) {
        return voucherRepository.findByAssignedCustomer(customerId);
    }

    public void retrieveVoucher(Voucher voucher) {
        voucher.retrieved();
    }

    public Optional<UUID> getAssignedCustomer(Voucher voucher) {
        return Optional.ofNullable(voucher.getAssignedCustomerId());

    }
}
