package com.prgrms.voucher_manage.domain.voucher.service;

import com.prgrms.voucher_manage.console.OutputUtil;
import com.prgrms.voucher_manage.console.VoucherType;
import com.prgrms.voucher_manage.domain.voucher.dto.CreateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.repository.VoucherRepository;
import com.prgrms.voucher_manage.exception.InvalidPercentException;
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
        VoucherType voucherType = dto.getVoucherType();
        switch (voucherType) {
            case FIXED -> voucherRepository.insert(new FixedAmountVoucher(dto.getDiscountAmount()));
            case PERCENT -> {
//                System.out.println("dto.isValidPercent() = " + dto.isValidPercent());
                if (!dto.isValidPercent()) {
                    throw new InvalidPercentException();
                }
                voucherRepository.insert(new PercentAmountVoucher(dto.getDiscountAmount()));
            }
        }
    }

    @Override
    public void showVoucherList() {
        List<Voucher> vouchers = voucherRepository.findAll();
        for (Voucher voucher : vouchers) {
            outputUtil.printVoucherInfo(voucher);
        }
    }
}
