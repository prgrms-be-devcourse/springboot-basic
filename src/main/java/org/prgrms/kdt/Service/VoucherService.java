package org.prgrms.kdt.Service;

import org.prgrms.kdt.Model.FixedAmountVoucher;
import org.prgrms.kdt.Model.PercentDiscountVoucher;
import org.prgrms.kdt.Model.Voucher;
import org.prgrms.kdt.Model.VoucherRequest;
import org.prgrms.kdt.Repository.VoucherRepository;
import org.prgrms.kdt.TypeStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// 서비스를 인터페이스로 정의하고 구현체를 만들수 있으나 구현체가 다양하지 않을게 명확할경우
// 인터페이스 없이 바로 구현클래스를 만드는건 절대 잘못된게 아닙니다. 불필요하게 인터페이스를
// 만들필요가 없어요 서비스는 클래스는요.
@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;


    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher %s".formatted(voucherId)));
    }


    public void useVoucher(Voucher voucher) {

    }
    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }


    public void createVoucher(UUID voucherId, TypeStatus type, VoucherRequest voucherRequest) {

        var voucher = getType(voucherId, type, voucherRequest.getDiscount());

        voucherRepository.save(voucher);
    }

    public Voucher getType(UUID voucherId, TypeStatus type, long discount) {
        if (type == TypeStatus.Fixed) return new FixedAmountVoucher(voucherId, discount);

        return new PercentDiscountVoucher(voucherId, discount);

    }

}


