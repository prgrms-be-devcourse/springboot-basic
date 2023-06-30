package com.programmers.springmission.voucher.application;

import com.programmers.springmission.voucher.domain.FixedAmountPolicy;
import com.programmers.springmission.voucher.domain.PercentDiscountPolicy;
import com.programmers.springmission.voucher.domain.Voucher;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.repository.VoucherRepository;
import com.programmers.springmission.voucher.request.VoucherCreateRequest;
import com.programmers.springmission.voucher.response.VoucherResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(VoucherCreateRequest voucherCreateRequest) {
        VoucherType voucherType = voucherCreateRequest.getVoucherType();
        Voucher voucher = switch (voucherType) {
            case FIXED_AMOUNT ->
                    new Voucher(UUID.randomUUID(), new FixedAmountPolicy(voucherCreateRequest.getAmount()), VoucherType.FIXED_AMOUNT);
            case PERCENT_DISCOUNT ->
                    new Voucher(UUID.randomUUID(), new PercentDiscountPolicy(voucherCreateRequest.getAmount()), VoucherType.PERCENT_DISCOUNT);
        };

        voucherRepository.save(voucher);
    }

    public List<VoucherResponse> findAllVoucher() {
        List<Voucher> voucherList = voucherRepository.findAll();
        return voucherList.stream()
                .map(VoucherResponse::new)
                .toList();
    }
}

