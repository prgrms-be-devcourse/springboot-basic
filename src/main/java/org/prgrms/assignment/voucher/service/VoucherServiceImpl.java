package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.dto.VoucherResponseDTO;
import org.prgrms.assignment.voucher.entity.VoucherEntity;
import org.prgrms.assignment.voucher.entity.VoucherHistoryEntity;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherStatus;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
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
    public Optional<VoucherResponseDTO> getVoucherById(UUID voucherId) {
        if(voucherRepository.findVoucherEntityById(voucherId).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(convertToVoucherResponseDTO(voucherRepository.
            findVoucherEntityById(voucherId).get()));
    }

    @Override
    public List<VoucherResponseDTO> getAllVoucherDTOs() {
        return voucherRepository.findAll().
                stream().
                map(voucherEntity -> new VoucherResponseDTO(voucherEntity.voucherId(),
                    voucherEntity.voucherType(),
                    voucherEntity.benefit(),
                    voucherEntity.createdAt(),
                    voucherEntity.expireDate())
                    )
                .toList();
    }

    @Override
    @Transactional
    public void updateVoucherEntity(Voucher voucher) {
        voucherRepository.update(convertToVoucherEntity(voucher));
        voucherRepository.insertVoucherHistoryEntity(convertToVoucherHistoryEntity(voucher, VoucherStatus.UPDATED));
    }

    @Override
    @Transactional
    public Voucher createVoucher(VoucherType voucherType, long benefit, long durationDate) {
        Voucher voucher = voucherFactory.createVoucher(voucherType, UUID.randomUUID(), benefit, durationDate);
        voucherRepository.insertVoucherEntity(convertToVoucherEntity(voucher));
        voucherRepository.insertVoucherHistoryEntity(convertToVoucherHistoryEntity(voucher, VoucherStatus.UNUSED));
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    private VoucherEntity convertToVoucherEntity(Voucher voucher) {
        return new VoucherEntity(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getCreatedAt(),
            voucher.getBenefit(), voucher.getExpireDate());
    }

    private VoucherHistoryEntity convertToVoucherHistoryEntity(Voucher voucher, VoucherStatus voucherStatus) {
        return new VoucherHistoryEntity(voucher.getVoucherId(), UUID.randomUUID(), voucherStatus, LocalDateTime.now());
    }

    private VoucherResponseDTO convertToVoucherResponseDTO(VoucherEntity voucherEntity) {
        return new VoucherResponseDTO(voucherEntity.voucherId(), voucherEntity.voucherType(), voucherEntity.benefit(),
            voucherEntity.createdAt(), voucherEntity.expireDate());
    }
}
