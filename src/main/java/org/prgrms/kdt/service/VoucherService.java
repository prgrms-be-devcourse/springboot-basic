package org.prgrms.kdt.service;

import org.prgrms.kdt.dao.entity.voucher.Voucher;
import org.prgrms.kdt.dao.entity.voucher.VoucherFactory;
import org.prgrms.kdt.dao.entity.voucher.VoucherType;
import org.prgrms.kdt.dao.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public void clear() {
        voucherRepository.clear();
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAllStoredVoucher();
    }

    public Voucher create(String voucherTypeNumber, String discountValue) {
        String voucherType = VoucherType.getStringClassName(voucherTypeNumber);
        Voucher voucher = voucherFactory.createVoucher(UUID.randomUUID(), discountValue, voucherType);
        voucherRepository.insert(voucher);
        return voucher;
    }
}
