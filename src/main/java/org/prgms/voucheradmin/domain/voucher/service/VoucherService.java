package org.prgms.voucheradmin.domain.voucher.service;

import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.dto.VoucherInputDto;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherInputDto voucherInputDto) {
        Voucher voucher = getVoucherInstance(voucherInputDto);

        return voucherRepository.save(voucher);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.getAll();
    }

    private Voucher getVoucherInstance(VoucherInputDto voucherInputDto) {
        VoucherType voucherType = voucherInputDto.getVoucherType();
        UUID voucherId = UUID.randomUUID();

        switch (voucherType) {
            case FIXED_AMOUNT:
                long amount = voucherInputDto.getAmount();
                return new FixedAmountVoucher(voucherId, amount);
            default:
                long percent = voucherInputDto.getAmount();
                return new PercentageDiscountVoucher(voucherId, percent);
        }
    }
}
