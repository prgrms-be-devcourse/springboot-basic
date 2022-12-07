package com.programmers.assignment.voucher.engine.service;

import com.programmers.assignment.voucher.engine.model.Voucher;
import com.programmers.assignment.voucher.engine.repository.VoucherRepository;
import com.programmers.assignment.voucher.util.domain.VoucherVariable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository, CustomerService customerService) {
        this.voucherRepository = voucherRepository;
    }


    public Voucher getVoucherById(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() ->
                        new RuntimeException(
                                MessageFormat.format("Can not find a voucher for {0}", voucherId))
                );
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher makeVoucher(String strCustomerId, String discountWay, String strDiscountValue) {
        var discountValue = Long.parseLong(strDiscountValue);
        var customerId = Long.parseLong(strCustomerId);
        var voucher = new Voucher(UUID.randomUUID(), VoucherVariable.chooseDiscountWay(discountWay), discountValue, customerId);
        return voucherRepository.insert(voucher);
    }
}
