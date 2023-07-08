package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherDto;
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
    public VoucherDto create(VoucherDto voucherDto) {
        VoucherType voucherType = VoucherType.findVoucherType(voucherDto.getVoucherType());
        Voucher voucher = switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(voucherDto.getAmount());
            case PERCENT -> new PercentAmountVoucher(voucherDto.getAmount());
        };

        Voucher savedVoucher = voucherRepository.save(voucher);

        return VoucherDto.toDto(savedVoucher.getVoucherType(), savedVoucher.getAmount());
    }

    @Override
    public List<VoucherDto> getAllVoucher() {
        List<Voucher> voucherList = voucherRepository.findAll();

        return voucherList.stream()
                .map(v -> new VoucherDto(v.getVoucherType(), v.getAmount()))
                .collect(Collectors.toUnmodifiableList());
    }
}
