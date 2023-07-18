package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.exception.VoucherIdNotFoundException;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponseDto;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.ropository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.*;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public VoucherResponseDto create(VoucherType userVoucherType, long amount) {
        Voucher voucher = userVoucherType.createVoucher(amount);
        Voucher savedVoucher = voucherRepository.save(voucher);

        return VoucherResponseDto.toDto(savedVoucher.getVoucherId(), savedVoucher.getVoucherType(), savedVoucher.getAmount());
    }

    @Override
    public VoucherResponseDto findById(UUID voucherId) {
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        Voucher voucher = foundVoucher.orElseThrow(() -> new VoucherIdNotFoundException(VOUCHER_ID_LOOKUP_FAILED));

        return VoucherResponseDto.toDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    @Override
    public List<VoucherResponseDto> findAll() {
        return voucherRepository.findAll()
                .stream()
                .map(v -> new VoucherResponseDto(v.getVoucherId(), v.getVoucherType(), v.getAmount()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public VoucherResponseDto update(UUID voucherId, VoucherType voucherType, long amount) {
        Optional<Voucher> updatedVoucher = voucherRepository.update(voucherId, voucherType, amount);
        Voucher voucher = updatedVoucher.orElseGet(() -> voucherRepository.save(voucherType.updateVoucher(voucherId, amount)));

        return VoucherResponseDto.toDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    @Override
    public void delete(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
