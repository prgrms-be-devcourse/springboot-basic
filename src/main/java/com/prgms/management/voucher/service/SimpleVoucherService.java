package com.prgms.management.voucher.service;

import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.model.VoucherType;
import com.prgms.management.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class SimpleVoucherService implements VoucherService {
    private final VoucherRepository voucherRepository;

    public SimpleVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public List<Voucher> findVouchers(VoucherType type, Timestamp start, Timestamp end) {
        if (type != null && start == null && end == null) {
            return voucherRepository.findByType(type);
        } else if (type == null && start != null && end != null) {
            return voucherRepository.findByDate(start, end);
        } else if (type != null && start != null && end != null) {
            return voucherRepository.findByTypeAndDate(type, start, end);
        } else {
            return voucherRepository.findAll();
        }
    }

    @Override
    public Voucher addVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher findVoucherById(UUID id) {
        return voucherRepository.findById(id);
    }

    @Override
    public void removeVoucherById(UUID id) {
        voucherRepository.removeById(id);
    }
}
