package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponseDto;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.PercentAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.ropository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {
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
    public List<VoucherResponseDto> getAllVoucher() {
        return voucherRepository.findAll()
                .stream()
                .map(v -> new VoucherResponseDto(v.getVoucherId(), v.getVoucherType(), v.getAmount()))
                .collect(Collectors.toUnmodifiableList());
    }
}
