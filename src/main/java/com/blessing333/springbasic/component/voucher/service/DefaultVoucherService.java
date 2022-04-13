package com.blessing333.springbasic.component.voucher.service;

import com.blessing333.springbasic.component.voucher.VoucherType;
import com.blessing333.springbasic.component.voucher.domain.FixedAmountVoucher;
import com.blessing333.springbasic.component.voucher.domain.PercentDiscountVoucher;
import com.blessing333.springbasic.component.voucher.domain.Voucher;
import com.blessing333.springbasic.component.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.component.voucher.exception.VoucherCreateFailException;
import com.blessing333.springbasic.component.voucher.repository.VoucherRepository;
import com.blessing333.springbasic.component.voucher.ui.VoucherManagerUserInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultVoucherService implements VoucherService {
    private final VoucherManagerUserInterface voucherManagerUserInterface;
    private final VoucherRepository repository;

    @Override
    public void createNewVoucher(ConvertedVoucherCreateForm form) {
        VoucherType type = form.getVoucherType();
        Voucher newVoucher;
        switch (type) {
            case FIXED -> newVoucher = new FixedAmountVoucher(form.getDiscountAmount());
            case PERCENT -> newVoucher = new PercentDiscountVoucher((int) form.getDiscountAmount());
            default -> throw new VoucherCreateFailException("");
        }
        repository.save(newVoucher);
    }

    @Override
    public void showVoucherList() {
        List<Voucher> savedVoucher = repository.findAll();
        savedVoucher.forEach(voucher -> voucherManagerUserInterface.printMessage(voucher.toString()));
    }
}
