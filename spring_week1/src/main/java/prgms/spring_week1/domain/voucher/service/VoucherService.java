package prgms.spring_week1.domain.voucher.service;

import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.impl.FixedAmountVoucher;
import prgms.spring_week1.domain.voucher.model.impl.PercentDiscountVoucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.exception.NoSuchOptionValue;
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

    public VoucherService(VoucherRepository voucherRepository, Output output, Input input) {
        this.voucherRepository = voucherRepository;
        this.output = output;
        this.input = input;
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
        output.printInsertFixedVoucherMessage();
        long DiscountAmount = input.insertDiscountAmountVoucher();
        return voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),VoucherType.FIXED,DiscountAmount));
    }

    private Voucher insertPercentDiscountVoucher() {
        output.printInsertPercentVoucherMessage();
        long FixedPercent = input.insertDiscountAmountVoucher();
        return voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(),VoucherType.PERCENT,FixedPercent));
    }

}
