package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.dto.VoucherDTO;
import org.prgrms.assignment.voucher.entity.VoucherEntity;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        return of(voucherRepository.
                findByID(voucherId).
                orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId)));
    }

    @Override
    public List<VoucherDTO> convertToVoucherDTOs() {
        return voucherRepository.findAll().
                stream().
                map(voucherEntity -> new VoucherDTO(voucherEntity.voucherType(),
                    voucherEntity.benefit(),
                    voucherEntity.createdAt()))
                .toList();
    }

    @Override
    @Transactional
    public void update(Voucher voucher) {
        voucherRepository.update(of(voucher));
    }

    @Override
    @Transactional
    public Voucher createVoucher(VoucherType voucherType, long benefit) {
        Voucher voucher = voucherFactory.createVoucher(voucherType, UUID.randomUUID(), benefit);
        voucherRepository.insert(of(voucher));
        return voucher;
    }

    private VoucherEntity of(Voucher voucher) {
        return new VoucherEntity(voucher.getVoucherId().toString().getBytes(), voucher.getVoucherType(), voucher.getCreatedAt(),
            voucher.getBenefit());
    }

    private Voucher of(VoucherEntity voucherEntity) {
        return voucherFactory.createVoucher(voucherEntity.voucherType(), UUID.nameUUIDFromBytes(voucherEntity.voucherId()),
            voucherEntity.benefit());
    }
}
