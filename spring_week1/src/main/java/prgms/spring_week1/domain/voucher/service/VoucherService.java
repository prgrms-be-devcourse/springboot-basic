package prgms.spring_week1.domain.voucher.service;

import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.impl.FixedAmountVoucher;
import prgms.spring_week1.domain.voucher.model.impl.PercentDiscountVoucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
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

    public VoucherService(VoucherRepository voucherRepository, Output output) {
        this.voucherRepository = voucherRepository;
        this.output = output;
    }

    public Voucher matchVoucherType(String inputSelectText){
        Optional<VoucherType> selectedVoucherType = Optional.ofNullable(Stream.of(VoucherType.values()).filter(voucherType -> voucherType.getVoucherType().equalsIgnoreCase(inputSelectText))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("wrong")));

        if(selectedVoucherType.get() == VoucherType.PERCENT) return insertPercentDiscountVoucher();
        else return insertFixedAmountVoucher();
    }

    private Voucher insertFixedAmountVoucher() {
        output.printInsertFixedVoucherMessage();
        long DiscountAmount = sc.nextLong();
        return voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),VoucherType.FIXED,DiscountAmount));
    }

    private Voucher insertPercentDiscountVoucher() {
        output.printInsertPercentVoucherMessage();
        long FixedPercent = sc.nextLong();
        return voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(),VoucherType.PERCENT,FixedPercent));
    }

}
