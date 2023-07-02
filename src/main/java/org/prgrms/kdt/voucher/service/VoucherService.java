package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.voucher.domain.FixedDiscountPolicy;
import org.prgrms.kdt.voucher.domain.PercentDiscountPolicy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.dto.CreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(CreateRequest request) {
        VoucherType voucherType = request.getVoucherType();
        double discountAmount = request.getDiscountAmount();

        return voucherRepository.insert(new Voucher(voucherType, voucherType.createPolicy(discountAmount)));
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
