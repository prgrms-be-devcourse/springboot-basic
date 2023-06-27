package com.prgms.voucher.voucherproject.service;

import com.prgms.voucher.voucherproject.domain.FixedAmountVoucher;
import com.prgms.voucher.voucherproject.domain.PercentDiscountVoucher;
import com.prgms.voucher.voucherproject.domain.Voucher;
import com.prgms.voucher.voucherproject.domain.VoucherType;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final Console console = new Console();

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void list(){
        ArrayList<Voucher> voucherArrayList = voucherRepository.findAll();
        if(voucherArrayList.size() == 0) {
            console.printNoVoucher();
        }
        if(voucherArrayList.size() > 0){
            voucherArrayList.forEach(voucher -> {console.printMsg(voucher.toString());});
        }
    }

    public void create(VoucherType voucherType){
        switch (voucherType) {
            case FIXED:
                console.printMsg("고정 할인 금액을 입력하세요. (1이상)");
                long discount = console.inputDiscountAmount();
                console.bufferDeleted();
                if(discount <= 0) {
                    throw new IllegalArgumentException("잘못된 고정 할인 금액입니다.");
                }
                Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), discount);
                voucherRepository.save(fixedAmountVoucher);
                break;

            case PERCENT:
                console.printMsg("퍼센트 할인 금액을 입력하세요. (1~100)");
                long percent = console.inputDiscountAmount();
                console.bufferDeleted();
                if(percent <= 0 || percent > 100) {
                    throw new IllegalArgumentException("잘못된 퍼센트 할인 금액입니다.");
                }
                Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
                voucherRepository.save(percentDiscountVoucher); //TODO : percentVoucher는 save 안 되는 문제 (UUID도 null로 들어감)
                break;

            default:
                throw new IllegalArgumentException("잘못된 Voucher Type 입니다.");
        }

    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

}
