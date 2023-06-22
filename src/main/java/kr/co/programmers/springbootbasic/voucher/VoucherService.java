package kr.co.programmers.springbootbasic.voucher;

import kr.co.programmers.springbootbasic.voucher.impl.FixedAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.impl.PercentAmountVoucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoucherService {
    private static final String NO_SAVED_VOUCHER = """
            생성된 바우처가 없습니다.
                        
            """;
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, long amount) {
        UUID voucherId = UUID.randomUUID();
        Voucher createdVoucher = switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER_COMMAND -> new FixedAmountVoucher(voucherId, amount);
            case PERCENT_AMOUNT_VOUCHER_COMMAND -> new PercentAmountVoucher(voucherId, amount);
        };

        return voucherRepository.save(createdVoucher);
    }

    public String listAllVoucher() {
        List<Voucher> vouchers = voucherRepository.listAll();
        if (vouchers.isEmpty()) {
            return NO_SAVED_VOUCHER;
        }
        return formatVoucherInfo(vouchers);
    }

    private String formatVoucherInfo(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\n"));
    }
}
