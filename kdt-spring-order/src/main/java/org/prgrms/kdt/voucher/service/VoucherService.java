package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class VoucherService {
    // Repository는 Service에서 사용, 관리함
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId){
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }
    // 사용자가 입력한 type에 맞는 voucher 생성
    public void createVoucher(UUID voucherId, VoucherType voucherType, long value){
        switch(voucherType){
            case FIXED -> voucherRepository.insert(new FixedAmountVoucher(voucherId, value));
            case PERCENT -> voucherRepository.insert(new PercentDiscountVoucher(voucherId, value));
        }
    }

    // voucher 리스트 -> controller로 반환
    public List<Voucher> getVoucherList(){
        return voucherRepository.getVoucherList();
    }




}
