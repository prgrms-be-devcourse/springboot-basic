package org.prgrms.kdt.service.voucher;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.entity.VoucherEntity;
import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        VoucherEntity voucherEntity = voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("can not find a voucher for" + voucherId));
        return toDomain(voucherEntity);
    }

    public List<Voucher> getVouchers() {
        List<VoucherEntity> voucherEntities = voucherRepository.findAll();
        return voucherEntities.stream().map(this::toDomain).collect(Collectors.toList());

    }


    public Voucher save(VoucherType type, Long discount) {
        VoucherType voucherType = type;
        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER, PERCENT_DISCOUNT_VOUCHER -> {
                System.out.println("voucherType = " + voucherType);
                return toDomain(voucherRepository.insert(VoucherEntity.toEntity(voucherType.makeVoucher(discount))));
            }
            default -> throw new RuntimeException("해당 바우처는 발급 불가능합니다");
        }

    }

    protected Voucher toDomain(VoucherEntity voucherEntity){
        switch (voucherEntity.getVoucherType()){
            case "FIXED_AMOUNT_VOUCHER":
                return new FixedAmountVoucher(voucherEntity.getVoucherId(),voucherEntity.getAmount());

            case "PERCENT_DISCOUNT_VOUCHER":
                return new PercentDiscountVoucher(voucherEntity.getVoucherId(), voucherEntity.getAmount());
            default:
                throw new IllegalArgumentException("잘못된 VoucherType 입니다.");
        }
    }

}
