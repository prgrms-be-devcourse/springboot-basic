package org.prgms.vouchermanagement.voucher.service;

import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;
import org.prgms.vouchermanagement.global.io.Console;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.prgms.vouchermanagement.voucher.domain.entity.VoucherImpl;
import org.prgms.vouchermanagement.voucher.domain.repository.VoucherRepository;
import org.prgms.vouchermanagement.voucher.exception.VoucherException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final Console console;

    public VoucherService(VoucherRepository voucherRepository, Console console) {
        this.voucherRepository = voucherRepository;
        this.console = console;
    }

    public void createNewVoucher() {
        Optional<Voucher> savedVoucher = Optional.empty();
        VoucherType typeToCreate = selectVoucherType();

        if (typeToCreate == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE) {
            long amountOrPercent = console.getFixedVoucherAmount();
            VoucherImpl voucher = new VoucherImpl(UUID.randomUUID(), amountOrPercent, VoucherType.FIXED_AMOUNT_VOUCHER_TYPE);
            savedVoucher = voucherRepository.saveVoucher(voucher);
        }
        if (typeToCreate == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            long amountOrPercent = console.getPercentDiscount();
            VoucherImpl voucher = new VoucherImpl(UUID.randomUUID(), amountOrPercent, VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE);
            savedVoucher = voucherRepository.saveVoucher(voucher);
        }

        if (savedVoucher.isEmpty()) {
            throw new VoucherException(ExceptionMessageConstant.VOUCHER_NOT_INSERTED_EXCEPTION);
        }
        console.printSavedFinished();
    }

    public VoucherType selectVoucherType() {
        console.printSelectVoucherType();
        return VoucherType.getVoucherType(console.getVoucherTypeInput());
    }

    public void showVoucherList() {
        console.printSelectVoucherListType();
        VoucherType typeToShow  = VoucherType.getVoucherType(console.getVoucherTypeInput());
        Map<UUID, Voucher> voucherList = voucherRepository.getVoucherList();
        console.printVoucherList(voucherList, typeToShow);
    }
}
