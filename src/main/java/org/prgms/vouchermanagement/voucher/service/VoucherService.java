package org.prgms.vouchermanagement.voucher.service;

import org.prgms.vouchermanagement.global.io.Console;
import org.prgms.vouchermanagement.voucher.domain.entity.FixedAmountVoucher;
import org.prgms.vouchermanagement.voucher.domain.entity.PercentDiscountVoucher;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.prgms.vouchermanagement.voucher.domain.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final Console console;

    public VoucherService(@Qualifier("jdbc") VoucherRepository voucherRepository, Console console) {
        this.voucherRepository = voucherRepository;
        this.console = console;
    }

    public void createNewVoucher() {
        VoucherType typeToCreate = selectVoucherType();
        long amountOrPercent = setDiscountAmount(typeToCreate);

        if (typeToCreate == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE) {
            FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amountOrPercent);
            voucherRepository.saveVoucher(voucher);
        }
        if (typeToCreate == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            PercentDiscountVoucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), amountOrPercent);
            voucherRepository.saveVoucher(voucher);
        }
        console.printSavedFinished();
    }

    public VoucherType selectVoucherType() {
        console.printSelectVoucherType();
        return VoucherType.getVoucherType(console.getVoucherTypeInput());
    }

    private long setDiscountAmount(VoucherType typeToCreate) {
        if (typeToCreate == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE) {
            return console.getFixedVoucherAmount();
        }
        if (typeToCreate == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            return console.getPercentDiscount();
        }
        return 0;
    }

    public void showVoucherList() {
        console.printSelectVoucherListType();
        VoucherType typeToShow  = VoucherType.getVoucherType(console.getVoucherTypeInput());
        Map<UUID, Voucher> voucherList = voucherRepository.getVoucherList();
        console.printVoucherList(voucherList, typeToShow);
    }
}
