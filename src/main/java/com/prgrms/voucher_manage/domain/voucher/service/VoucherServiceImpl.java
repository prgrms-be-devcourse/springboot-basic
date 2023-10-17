package com.prgrms.voucher_manage.domain.voucher.service;

import com.prgrms.voucher_manage.console.VoucherType;
import com.prgrms.voucher_manage.domain.voucher.dto.CreateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.repository.VoucherRepository;
import com.prgrms.voucher_manage.exception.InvalidDiscountRange;
import com.prgrms.voucher_manage.util.OutputUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final OutputUtil outputUtil;


    @Override
    public void createVoucher(CreateVoucherDto dto) {
        VoucherType voucherType = dto.voucherType();
        switch (voucherType) {
            case FIXED -> {
                if (dto.isInValidPrice()) throw new InvalidDiscountRange();
                voucherRepository.insert(new FixedAmountVoucher(dto.discountAmount()));
            }
            case PERCENT -> {
                if (dto.isInvalidPercent()) throw new InvalidDiscountRange();
                voucherRepository.insert(new PercentAmountVoucher(dto.discountAmount()));
            }
        }
    }

    @Override
    public void showVoucherList() {
        List<Voucher> vouchers = voucherRepository.findAll();
        vouchers.forEach(voucher -> {
            switch (voucher.getVoucherType()) {
                case FIXED -> outputUtil.printFixedVoucherInfo(voucher);
                case PERCENT -> outputUtil.printPercentVoucherInfo(voucher);
            }
        });
    }
}
