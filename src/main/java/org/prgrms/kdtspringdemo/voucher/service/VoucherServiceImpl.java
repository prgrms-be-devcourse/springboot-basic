package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherDto;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.PercentAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.ropository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {
    private static final String INVALID_VOUCHER_TYPE = "바우처 형식이 알맞지 않습니다.";
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public VoucherDto createVoucher(VoucherDto voucherDto) {
        switch (voucherDto.getVoucherType()) {
            case FIXED -> {
                Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), voucherDto.getVoucherType(), voucherDto.getDiscount());
                Voucher voucher = voucherRepository.save(fixedAmountVoucher);
                return VoucherDto.toDto(voucher);
            }
            case PERCENT -> {
                Voucher percentAmountVoucher = new PercentAmountVoucher(UUID.randomUUID(), voucherDto.getVoucherType(), voucherDto.getDiscount());
                Voucher voucher = voucherRepository.save(percentAmountVoucher);
                return VoucherDto.toDto(voucher);
            }
            default -> {
                throw new IllegalArgumentException(INVALID_VOUCHER_TYPE);
            }
        }
    }

    @Override
    public List<VoucherDto> getAllVoucher() {
        List<Voucher> voucherList = voucherRepository.findAll();
        return voucherList.stream()
                .map(v -> new VoucherDto(v.getVoucherType(), v.getDiscount()))
                .collect(Collectors.toList());
    }
}
