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

    public Voucher createVoucher(VoucherType type) throws NumberFormatException {
        Voucher voucher = null;

        switch (type) {
            case FIXED_AMOUNT:
                System.out.print("할인 금액을 입력하세요 ==> ");
                String fixedAmount = sc.next();
                voucher = new FixedAmountVoucher(UUID.randomUUID(), Long.valueOf(fixedAmount));
                break;
            case PERCENT_DISCOUNT:
                System.out.print("할인율을 입력하세요 ==> ");
                String percent = sc.next();
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), Double.valueOf(percent));
                break;
            default:
                //추가 로직
                break;
        }

        System.out.println("할인권이 정상적으로 생성 및 저장되었습니다. " +
                "(생성된 할인권 ID: " + voucher.getVoucherId() + ")");
        Voucher result = voucherRepository.insert(voucher);

        return result;
    }

    public Optional<Voucher> findVoucher(String voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

}
