package prgms.spring_week1.domain.voucher.service;

import org.springframework.beans.factory.annotation.Qualifier;
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
        VoucherType selectedVoucherType = matchVoucherTypeFilter(inputSelectText);

        return selectedVoucherType;
    }

    private VoucherType matchVoucherTypeFilter(String inputSelectText) {
        Optional<VoucherType> selectedVoucherType = getMatchVoucherType(Stream.of(VoucherType.values()), inputSelectText);
        if(selectedVoucherType.isEmpty()) {
            throw new NoSuchVoucherType();
        }
        return selectedVoucherType.get();
    }

    private Optional<VoucherType> getMatchVoucherType(Stream<VoucherType> voucherType, String inputSelectText) {
        VoucherType matchedVoucherType = voucherType.filter(v -> v.getVoucherType().equalsIgnoreCase(inputSelectText))
                                        .findFirst()
                                        .orElseThrow(() -> new NoSuchVoucherType("해당 바우처 타입이 존재하지 않습니다."));

        return Optional.ofNullable(matchedVoucherType);
    }

    public Voucher insertFixedAmountVoucher(Long discountAmount) {
        return voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED, discountAmount));
    }

    public Voucher insertPercentDiscountVoucher(int fixedAmount) {
        return voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT, fixedAmount));
    }

}
