package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponseDto;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.PercentAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.ropository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {
    private static final String NOT_FOUND_VOUCHER = "바우처를 찾지 못했습니다.";

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public VoucherResponseDto create(VoucherType userVoucherType, long amount) {
        VoucherType voucherType = VoucherType.findVoucherType(userVoucherType);
        Voucher voucher = switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(amount);
            case PERCENT -> new PercentAmountVoucher(amount);
        };

        Voucher savedVoucher = voucherRepository.save(voucher);

        return VoucherResponseDto.toDto(savedVoucher.getVoucherId(), savedVoucher.getVoucherType(), savedVoucher.getAmount());
    }

    @Override
    public VoucherResponseDto getVoucher(UUID voucherId) {
        Voucher voucher = validateExist(voucherRepository.findById(voucherId));
        return VoucherResponseDto.toDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    private Voucher validateExist(Voucher voucher) {
        if (voucher == null) {
            throw new NoSuchElementException(NOT_FOUND_VOUCHER);
        }

        return voucher;
    }

    @Override
    public List<VoucherResponseDto> getAllVoucher() {
        return voucherRepository.findAll()
                .stream()
                .map(v -> new VoucherResponseDto(v.getVoucherId(), v.getVoucherType(), v.getAmount()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public VoucherResponseDto updateVoucher(UUID voucherId, VoucherType voucherType, long amount) {
        Voucher voucher = switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(voucherId, amount);
            case PERCENT -> new PercentAmountVoucher(voucherId, amount);
        };

        Voucher updateVoucher = validateExist(voucherRepository.update(voucher));

        return VoucherResponseDto.toDto(updateVoucher.getVoucherId(), updateVoucher.getVoucherType(), updateVoucher.getAmount());
    }

    @Override
    public VoucherResponseDto deleteVoucher(UUID voucherId) {
        Voucher deleteVoucher = voucherRepository.delete(voucherId);

        return VoucherResponseDto.toDto(deleteVoucher.getVoucherId(), deleteVoucher.getVoucherType(), deleteVoucher.getAmount());
    }
}
