package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.dto.VoucherDTO;
import org.prgrms.assignment.voucher.entity.VoucherEntity;
import org.prgrms.assignment.voucher.entity.VoucherHistoryEntity;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherStatus;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        return convertToVoucher(voucherRepository.
                findByID(voucherId).
                orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId)));
    }

    @Override
    public List<VoucherDTO> convertToVoucherDTOs() {
        return voucherRepository.findAll().
                stream().
                map(voucherEntity -> new VoucherDTO(voucherEntity.voucherType(),
                    voucherEntity.benefit(),
                    voucherEntity.createdAt(),
                    voucherEntity.expireDate())
                    )
                .toList();
    }

    @Override
    @Transactional
    public void update(Voucher voucher) {
        voucherRepository.update(convertToVoucherEntity(voucher), convertToVoucherHistoryEntity(voucher, VoucherStatus.UPDATED));
    }

    @Override
    @Transactional
    public Voucher createVoucher(VoucherType voucherType, long benefit, long durationDate) {
        Voucher voucher = voucherFactory.createVoucher(voucherType, UUID.randomUUID(), benefit, durationDate);
        voucherRepository.insert(convertToVoucherEntity(voucher), convertToVoucherHistoryEntity(voucher, VoucherStatus.UNUSED));
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    private VoucherEntity convertToVoucherEntity(Voucher voucher) {
        return new VoucherEntity(voucher.getVoucherId().toString().getBytes(), voucher.getVoucherType(), voucher.getCreatedAt(),
            voucher.getBenefit(), voucher.getExpireDate());
    }

    private VoucherHistoryEntity convertToVoucherHistoryEntity(Voucher voucher, VoucherStatus voucherStatus) {
        return new VoucherHistoryEntity(voucher.getVoucherId().toString().getBytes(), voucherStatus, LocalDateTime.now());
    }

    private Voucher convertToVoucher(VoucherEntity voucherEntity) {
        int durationDate = (int) ChronoUnit.DAYS.between(voucherEntity.createdAt(), voucherEntity.expireDate());
        return voucherFactory.createVoucher(voucherEntity.voucherType(), UUID.nameUUIDFromBytes(voucherEntity.voucherId()),
            voucherEntity.benefit(), durationDate);
    }
}
