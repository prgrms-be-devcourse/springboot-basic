package org.prgrms.kdt.service;

import lombok.AllArgsConstructor;
import org.prgrms.kdt.domain.*;
import org.prgrms.kdt.factory.VoucherFactory;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VoucherService {
    @Autowired
    private final MemoryVoucherRepository memoryVoucherRepository;

    @Autowired
    private final VoucherFactory voucherFactory;

    //factory로 관리하게끔 수정
    public Voucher createVoucher(VoucherType voucherType){
        Voucher v = voucherFactory.getDiscounterVoucher(voucherType);
        return v;
    }

    public Voucher getVoucher(UUID voucherId) {
        return memoryVoucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(String.format("Can not find a voucher %s", voucherId)));
    }

    public List<Voucher> findAll() {
        return memoryVoucherRepository.findAll();
    }

    //사용안함
    public void useVoucher(Voucher voucher){
    }


//    public Voucher createVoucher(UUID voucherId, long discount, int voucherType){
////        return memoryVoucherRepository.insert2(new FixedAmountVoucher(voucherId,discount));
//        Voucher voucher = null;
//
//        if(voucherType == 1){
//            return memoryVoucherRepository.insert2(new FixedAmountVoucher(voucherId,discount));
//        }else{
//            return memoryVoucherRepository.insert2(new PercentDiscountVoucher(voucherId,discount));
//        }
//    }

    //의존성 주입에 있어서 (생성자 생성, 필드에 autowired, 하면 주입이 된다.)

//    public VoucherService(MemoryVoucherRepository memoryVoucherRepository) {
//        this.memoryVoucherRepository = memoryVoucherRepository;
//    }



}

