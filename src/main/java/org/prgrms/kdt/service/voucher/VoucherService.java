package org.prgrms.kdt.service.voucher;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.entity.VoucherEntity;
import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucherById(Long voucherId) {
        try {
            VoucherEntity voucherEntity = voucherRepository.findById(voucherId);
            return toDomain(voucherEntity);
        } catch (Exception e) {
            throw new RuntimeException("can not find a voucher for" + voucherId);
        }
    }

    public List<Voucher> getVouchersByType(String voucherType){
        List<VoucherEntity> voucherEntities = voucherRepository.findByType(voucherType);
        return voucherEntities.stream().map(this::toDomain).collect(Collectors.toList());
    }

    public List<Voucher> getVouchers() {
        List<VoucherEntity> voucherEntities = voucherRepository.findAll();
        return voucherEntities.stream().map(this::toDomain).collect(Collectors.toList());

    }

    public Voucher save(VoucherType type, Long discount) {
        switch (type) {
            case FIXED, PERCENT -> {
                return toDomain(voucherRepository.insert(VoucherEntity.from(type.makeVoucher(discount))));
            }
            default -> throw new RuntimeException("해당 바우처는 발급 불가능합니다");
        }
    }

    public void deleteById(Long voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    protected Voucher toDomain(VoucherEntity voucherEntity){
        switch (voucherEntity.getVoucherEntityType()){
            case "FIXED":
                return new FixedAmountVoucher(voucherEntity.getVoucherEntityId(),voucherEntity.getEntityAmount());

            case "PERCENT":
                return new PercentDiscountVoucher(voucherEntity.getVoucherEntityId(), voucherEntity.getEntityAmount());
//            default:
//                throw new IllegalArgumentException("잘못된 VoucherType 입니다.");
        }
    }

}
