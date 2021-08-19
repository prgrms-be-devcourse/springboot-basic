package org.prgrms.kdtbespring.voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final List<Voucher> vouchers = new ArrayList<>();
    private VoucherRepository voucherRepository;


    public VoucherService(@Qualifier("memory") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }


    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a Voucher for {0}", voucherId)));
    }

    // 바우처 사용했을 때
    public void useVoucher(Voucher voucher) {
    }

    // Voucher 타입 생성
    public Optional<Voucher> create(VoucherType voucherType){
        UUID voucherId = UUID.randomUUID();
        if (voucherType.equals(VoucherType.FixedAmountVoucher) ){
            // 임의로 지정 10원 할인
            long amount = 10L;
            FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, amount);
            vouchers.add(fixedAmountVoucher);
            return Optional.of(fixedAmountVoucher);
        }else if (voucherType.equals(VoucherType.PercentDiscountVoucher)){
            // 임의로 지정 10%
            long percent = 10L;
            PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, percent);
            vouchers.add(percentDiscountVoucher);
            return Optional.of(percentDiscountVoucher);
        }
        return Optional.empty();
    }

    // 리스트 반환
    public List<Voucher> list(){
        return vouchers;
    }

    /*public void setVoucherRepository(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }*/

}
