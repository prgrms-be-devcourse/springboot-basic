package com.programmers.springmission.voucher.application;

import com.programmers.springmission.voucher.domain.FixedAmountPolicy;
import com.programmers.springmission.voucher.domain.PercentDiscountPolicy;
import com.programmers.springmission.voucher.domain.Voucher;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.presentation.request.VoucherCreateRequest;
import com.programmers.springmission.voucher.presentation.response.VoucherResponse;
import com.programmers.springmission.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse createVoucher(VoucherCreateRequest voucherCreateRequest) {
        VoucherType voucherType = voucherCreateRequest.getVoucherType();
        Voucher voucher = switch (voucherType) {
            case FIXED_AMOUNT ->
                    new Voucher(new FixedAmountPolicy(voucherType), voucherCreateRequest.getAmount());
            case PERCENT_DISCOUNT ->
                    new Voucher(new PercentDiscountPolicy(voucherType), voucherCreateRequest.getAmount());
        };

        voucherRepository.save(voucher);
        return new VoucherResponse(voucher);
    }

    public List<VoucherResponse> findAllVoucher() {
        List<Voucher> voucherList = voucherRepository.findAll();
        return voucherList.stream()
                .map(VoucherResponse::new)
                .toList();
    }
}

