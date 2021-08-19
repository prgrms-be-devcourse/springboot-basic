package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;


@Service
public class VoucherService {
    // Repository는 Service에서 사용, 관리함
    private final VoucherRepository voucherRepository;
    // voucher 파일 저장 경로
    private final String filePath = "C:/Users/NB1/Desktop/PROGRAM/GitWorkSpace/Programmers_Devcourse/w3-SpringBoot_Part_A/kdt-spring-order/";
    private final String fileName = "voucher_list.csv";

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

    // -------------------------------------- ( 내가 작성한 부분 ) --------------------------------------

    // 사용자가 입력한 type에 맞는 voucher 생성
    public void createVoucher(VoucherType voucherType, long value){
        if(voucherType == VoucherType.fixed){
            voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), value));
        }
        else if(voucherType == VoucherType.percent){
            voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), value));
        }
    }

    // voucher 리스트 -> controller로 반환
    public List<Voucher> getVoucherList(){
        return voucherRepository.getVoucherList();
    }

    //
    public void saveVoucherList() throws IOException {

    }


}
