package prgms.spring_week1.domain.voucher.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.impl.FixedAmountVoucher;
import prgms.spring_week1.domain.voucher.model.impl.PercentDiscountVoucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.domain.voucher.service.validation.impl.DiscountAmountValidation;
import prgms.spring_week1.domain.voucher.service.validation.impl.PercentDiscountValidation;
import prgms.spring_week1.exception.NoSuchVoucherType;
import prgms.spring_week1.io.Input;
import prgms.spring_week1.io.Output;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class VoucherService {
    Scanner sc = new Scanner(System.in);
    private final VoucherRepository voucherRepository;
    private final Output output;
    private final Input input;
    private final DiscountAmountValidation discountAmountValidation;
    private final PercentDiscountValidation percentDiscountValidation;

    public VoucherService(VoucherRepository voucherRepository, Output output, Input input, @Qualifier("amount") DiscountAmountValidation discountAmountValidation,@Qualifier("percent") PercentDiscountValidation percentDiscountValidation) {
        this.voucherRepository = voucherRepository;
        this.output = output;
        this.input = input;
        this.discountAmountValidation = discountAmountValidation;
        this.percentDiscountValidation = percentDiscountValidation;
    }

    public Voucher matchVoucherType(String inputSelectText) throws NoSuchVoucherType {
        Optional<VoucherType> selectedVoucherType = Optional.ofNullable(Stream.of(VoucherType.values()).filter(voucherType -> voucherType.getVoucherType().equalsIgnoreCase(inputSelectText))
                .findFirst()
                .orElseThrow(() -> new NoSuchVoucherType("해당 바우처 타입이 존재하지 않습니다.")));

        if(selectedVoucherType.get() == VoucherType.PERCENT) {
            return insertPercentDiscountVoucher();
        }
        else {
            return insertFixedAmountVoucher();
        }
    }

    private Voucher insertFixedAmountVoucher() {
        boolean IS_VALID = true;
        long discountAmount = 0;
        while (IS_VALID){
            output.printInsertFixedVoucherMessage();
            discountAmount = input.insertDiscountAmountVoucher();
            IS_VALID = discountAmountValidation.invalidateInsertDiscountValue(discountAmount);
        }
        return voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),VoucherType.FIXED,discountAmount));
    }

    private Voucher insertPercentDiscountVoucher() {
        boolean IS_VALID = true;
        long fixedAmount = 0;
        while (IS_VALID){
            output.printInsertPercentVoucherMessage();
            fixedAmount = input.insertDiscountAmountVoucher();
            IS_VALID = percentDiscountValidation.invalidateInsertDiscountValue(fixedAmount);
        }
        return voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(),VoucherType.PERCENT,fixedAmount));
    }

}
