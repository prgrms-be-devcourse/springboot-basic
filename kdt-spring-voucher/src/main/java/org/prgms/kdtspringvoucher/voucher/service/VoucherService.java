package org.prgms.kdtspringvoucher.voucher.service;

import org.prgms.kdtspringvoucher.voucher.domain.*;
import org.prgms.kdtspringvoucher.voucher.dto.UpdateVoucherRequest;
import org.prgms.kdtspringvoucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void showAllVoucher() {
        List<Voucher> vouchers = voucherRepository.findAll();
        if (vouchers.isEmpty())
            System.out.println("No vouchers.....\n");
        else {
            vouchers.forEach(voucher -> System.out.println((vouchers.indexOf(voucher) + 1) + ". " + voucher));
        }
    }

    public Voucher createVoucher(VoucherType voucherType,Long amount) {
        switch (voucherType) {
            case FIXED -> {
                return voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(),amount,voucherType, LocalDateTime.now()));
            }
            case PERCENT -> {
                return voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), amount, voucherType, LocalDateTime.now()));
            }
        }
        return null;
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchers(VoucherType voucherType, LocalDateTime from, LocalDateTime to) {
        return voucherRepository.findByParam(voucherType, from, to);
    }

    public Optional<Voucher> getVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> getVouchersByCustomerId(UUID customerId) {
        return voucherRepository.findByCustomerId(customerId);
    }

    public Voucher updateVoucherById(UUID voucherId, UpdateVoucherRequest updateVoucherRequest) {
        Voucher voucher = voucherRepository.findById(voucherId).orElse(null);
        voucher.changeAmount(updateVoucherRequest.getAmount());
        return voucherRepository.update(voucher);
    }

    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public void deleteVoucherById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
