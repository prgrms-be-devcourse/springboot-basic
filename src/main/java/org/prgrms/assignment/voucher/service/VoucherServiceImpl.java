package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.dto.VoucherResponseDTO;
import org.prgrms.assignment.voucher.dto.VoucherServiceRequestDTO;
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
    public Optional<VoucherResponseDTO> getVoucherById(UUID voucherId) {
        if(voucherRepository.findVoucherEntityById(voucherId).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(VoucherResponseDTO.of(voucherRepository.
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

    // repo에서 한번에 해주기
    @Override
    @Transactional
    public void updateVoucherEntity(VoucherServiceRequestDTO voucher) {
        voucherRepository.update(VoucherEntity.of(voucher), VoucherHistoryEntity.of(voucher, VoucherStatus.UPDATED));
    }


    // repo에서 한번에 해주기
    @Override
    @Transactional
    public Voucher createVoucher(VoucherServiceRequestDTO voucherServiceRequestDTO) {
        Voucher voucher = voucherFactory.createVoucher(voucherServiceRequestDTO);
        voucherRepository.insert(VoucherEntity.of(voucherServiceRequestDTO), VoucherHistoryEntity.of(voucherServiceRequestDTO, VoucherStatus.UNUSED));
        return voucher;
    }

    @Override
    @Transactional
    public void delete(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    @Override
    public List<VoucherResponseDTO> getVouchersByType(VoucherType voucherType) {
        return voucherRepository.findVouchersByType(voucherType)
            .stream()
            .map(voucherEntity -> new VoucherResponseDTO(voucherEntity.voucherId(),
                voucherEntity.voucherType(),
                voucherEntity.benefit(),
                voucherEntity.createdAt(),
                voucherEntity.expireDate())
            )
            .toList();
    }

}
