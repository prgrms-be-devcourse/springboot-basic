package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.PercentDiscountVoucher;
import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.domain.VoucherType;
import com.programmers.springbootbasic.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final Scanner sc = new Scanner(System.in);

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType type) {
        Voucher voucher;

        switch (type) {
            case FIXED_AMOUNT:
                System.out.print("할인 금액을 입력하세요 ==> ");
                Long fixedAmount = sc.nextLong();
                if (fixedAmount <= 0 || fixedAmount >= FixedAmountVoucher.FIXED_AMOUNT_UPPER_LIMIT)
                    throw new IllegalArgumentException("유효한 값이 아닙니다.");
                voucher = new FixedAmountVoucher(UUID.randomUUID(), fixedAmount);
                break;
            default:
                System.out.print("할인율을 입력하세요 ==> ");
                double percent = sc.nextDouble();
                if (percent <= 0.0 || percent > 100.0)
                    throw new IllegalArgumentException("유효한 값이 아닙니다.");
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
                break;
        }
        System.out.println("할인권이 정상적으로 생성 및 저장되었습니다. " +
                "(생성된 할인권 ID: " + voucher.getVoucherId() + ")");
        voucherRepository.insert(voucher);

        return voucher;
    }

    public Optional<Voucher> findVoucher(String voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

}
