package org.prgms.voucheradmin.domain.voucher.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.dto.VoucherInputDto;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherInputDto voucherInputDto) throws IOException {
        Voucher voucher = getVoucherInstance(voucherInputDto);

        return voucherRepository.save(voucher);
    }

    public List<Voucher> getVouchers() throws IOException{
        return voucherRepository.getAll();
    }

    private Voucher getVoucherInstance(VoucherInputDto voucherInputDto) {
        VoucherTypes voucherTypes = voucherInputDto.getVoucherType();
        UUID voucherId = UUID.randomUUID();

        switch (voucherTypes) {
            case FIXED_AMOUNT:
                long amount = voucherInputDto.getAmount();
                return new FixedAmountVoucher(voucherId, amount);
            default:
                long percent = voucherInputDto.getAmount();
                return new PercentageDiscountVoucher(voucherId, percent);
        }
    }
}
