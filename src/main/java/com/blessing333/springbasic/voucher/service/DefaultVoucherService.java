package com.blessing333.springbasic.voucher.service;

import com.blessing333.springbasic.voucher.VoucherType;
import com.blessing333.springbasic.voucher.domain.FixedAmountVoucher;
import com.blessing333.springbasic.voucher.domain.PercentDiscountVoucher;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.exception.VoucherCreateFailException;
import com.blessing333.springbasic.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultVoucherService implements VoucherService {
    private final VoucherRepository repository;

    @Override
    public Voucher createNewVoucher(ConvertedVoucherCreateForm form) {
        VoucherType type = form.getVoucherType();
        Voucher newVoucher;
        switch (type) {
            case FIXED -> newVoucher = new FixedAmountVoucher(form.getDiscountAmount());
            case PERCENT -> newVoucher = new PercentDiscountVoucher((int) form.getDiscountAmount());
            default -> {
                log.error("검증되지 않은 VoucherType이 서비스로 넘어옴.validator 확인 필요.\n type -> {}", type);
                throw new VoucherCreateFailException("바우처 생성 실패");
            }
        }
        repository.save(newVoucher);
        return newVoucher;
    }

    @Override
    public List<Voucher> loadAllVoucher() {
        return repository.findAll();
    }
}
