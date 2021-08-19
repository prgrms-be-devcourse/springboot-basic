package org.prgrms.kdt.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class VoucherService {

//    @Autowired
//    private final VoucherMemoryRepo voucherMemoryRepo;

    @Autowired
    private final MemoryVoucherRepository memoryVoucherRepository;

    //의존성 주입에 있어서 (생성자 생성, 필드에 autowired, 하면 주입이 된다.)

//    public VoucherService(MemoryVoucherRepository memoryVoucherRepository) {
//        this.memoryVoucherRepository = memoryVoucherRepository;
//    }

    public Voucher getVoucher(UUID voucherId) {
        return memoryVoucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(String.format("Can not find a voucher %s", voucherId)));
    }

    //사용안함
    public void useVoucher(Voucher voucher){
    }

    public Voucher createVoucher(UUID voucherId, long discount, int voucherType){
        System.out.println("discount 값 :"+discount);
        System.out.println("voucher ID :"+voucherId);
        System.out.println("type :"+voucherType);
        if(voucherType == 1){
            return memoryVoucherRepository.insert2(new FixedAmountVoucher(voucherId,discount));
        }else{
            return memoryVoucherRepository.insert2(new PercentDiscountVoucher(voucherId,discount));
        }
    }

    public List<Voucher> findAll() {
        return memoryVoucherRepository.findAll();
    }

}
