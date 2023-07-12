package org.prgms.vouchermanagement.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.global.io.Console;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final Console console;

    public void createNewVoucher() {
        VoucherType voucherType = selectVoucherType();
        long amountOrPercent = 0;

        if (voucherType == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE) {
            amountOrPercent = console.getFixedVoucherAmount();
        } else if (voucherType == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            amountOrPercent = console.getPercentDiscount();
        }

        Voucher voucher = new Voucher(UUID.randomUUID(), amountOrPercent, VoucherType.FIXED_AMOUNT_VOUCHER_TYPE);
        voucherRepository.save(voucher);
        console.printSavedFinished();
    }

    private VoucherType selectVoucherType() {
        console.printSelectVoucherType();
        return VoucherType.getVoucherType(console.getVoucherTypeInput());
    }

    public void showVoucherList() {
        console.printSelectVoucherListType();
        VoucherType typeToShow  = VoucherType.getVoucherType(console.getVoucherTypeInput());
        List<Voucher> voucherList = voucherRepository.findAll();
        console.printVoucherList(voucherList, typeToShow);
    }
}
