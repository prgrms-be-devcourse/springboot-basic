package com.blessing333.springbasic.voucher.service;

import com.blessing333.springbasic.ui.UserInterface;
import com.blessing333.springbasic.voucher.VoucherCreateFailException;
import com.blessing333.springbasic.voucher.VoucherType;
import com.blessing333.springbasic.voucher.domain.FixedAmountVoucher;
import com.blessing333.springbasic.voucher.domain.PercentDiscountVoucher;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultVoucherService implements VoucherService {
    private final UserInterface userInterface;
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
        savedVoucher.forEach(voucher -> userInterface.printMessage(voucher.toString()));
    }
}
