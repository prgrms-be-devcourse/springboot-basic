package org.programmers.kdt.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private VoucherFactory voucherFactory;

    @Autowired
    public VoucherService(@Qualifier("File") VoucherRepository voucherRepository) {
        // @Qualifier("Memory") for Homework W3D2
        // @Qualifier("File") for Homework W3D3
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return this.voucherRepository
                .findById(voucherId)
                .orElseThrow( () -> new RuntimeException(MessageFormat.format("Cannot find a voucher for {0}", voucherId)) );
    }

    public void useVoucher(Voucher voucher) {
        // TODO: TO BE IMPLEMENTED...
    }

    public void addVoucher(Voucher voucher) {
        this.voucherRepository.save(voucher);
    }

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, long discount) {
        // TODO: 팩토리 구상체 설정을 해 주는 가장 적절한 방법 생각 해 보기
        switch (voucherType) {
            case FIXED -> voucherFactory = new FixedAmountVoucherFactory();
            case PERCENT -> voucherFactory = new PercentDiscountVoucherFactory();
        }
        Voucher voucher = this.voucherFactory.createVoucher(voucherId, discount);
        this.addVoucher(voucher);
        return voucher;
    }

    public List<Voucher> getAllVoucher() {
        return this.voucherRepository.findAll();
    }
}
