package prgms.spring_week1.domain.voucher.service;

import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.impl.FixedAmountVoucher;
import prgms.spring_week1.domain.voucher.model.impl.PercentDiscountVoucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.exception.NoSuchVoucherType;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherType matchVoucherType(String inputSelectText) throws NoSuchVoucherType {
        VoucherType selectedVoucherType = makeVoucherTypeStream(inputSelectText);

        return selectedVoucherType;
    }

    private VoucherType makeVoucherTypeStream(String inputSelectText) {
        Optional<VoucherType> matchedVoucherType = getMatchVoucherTypeFilter(Stream.of(VoucherType.values()), inputSelectText);
        if (matchedVoucherType.isEmpty()) {
            throw new NoSuchVoucherType();
        }
        return matchedVoucherType.get();
    }

    private Optional<VoucherType> getMatchVoucherTypeFilter(Stream<VoucherType> voucherType, String inputSelectText) {
        return voucherType.filter(v -> v.getVoucherType().equalsIgnoreCase(inputSelectText))
                .findFirst();
    }

    public Voucher insertFixedAmountVoucher(Long discountAmount) {
        return voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED, discountAmount));
    }

    public Voucher insertPercentDiscountVoucher(int fixedAmount) {
        return voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT, fixedAmount));
    }

}
