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
    public VoucherDto create(VoucherDto voucherDto) {
        Voucher voucher;
        switch (voucherDto.getVoucherType()) {
            case FIXED -> {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), voucherDto.getVoucherType(), voucherDto.getAmount());
            }
            case PERCENT -> {
                voucher = new PercentAmountVoucher(UUID.randomUUID(), voucherDto.getVoucherType(), voucherDto.getAmount());
            }
            default -> {
                throw new IllegalArgumentException(INVALID_VOUCHER_TYPE);
            }
        }

        Voucher savedVoucher = voucherRepository.save(voucher);
        return convertToVoucherDto(savedVoucher);
    }

    private VoucherDto convertToVoucherDto(Voucher voucher) {
        return new VoucherDto(
                voucher.getVoucherType(),
                voucher.getAmount()
        );
    }

    @Override
    public List<VoucherDto> getAllVoucher() {
        List<Voucher> voucherList = voucherRepository.findAll();

        return voucherList.stream()
                .map(VoucherDto::new)
                .collect(Collectors.toList());
    }
}
