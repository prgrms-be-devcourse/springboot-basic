package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.dto.VoucherDTO;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService{

    private final VoucherRepository voucherRepository;

    private final VoucherFactory voucherFactory;

    public VoucherServiceImpl(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public Voucher getVoucherById(UUID voucherId) {
        return voucherRepository.
                findByID(voucherId).
                orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    @Override
    public List<VoucherDTO> convertToVoucherDTOs() {
        return voucherRepository.findAll().
                stream().
                map(voucher -> new VoucherDTO(voucher.getVoucherType(),
                    voucher.getBenefit(),
                    voucher.getCreatedAt()))
                .toList();
    }

    @Override
    @Transactional
    public void update(Voucher voucher) {
        voucherRepository.update(voucher);
    }

    @Override
    @Transactional
    public Voucher createVoucher(VoucherType voucherType, long benefit) {
        Voucher voucher = voucherFactory.createVoucher(voucherType, UUID.randomUUID(), benefit);
        return voucherRepository.insert(voucher);
    }
}
