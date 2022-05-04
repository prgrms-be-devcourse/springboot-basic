package com.prgrms.kdt.springbootbasic.service;

import com.prgrms.kdt.springbootbasic.VoucherList;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import com.prgrms.kdt.springbootbasic.exception.JdbcQueryFail;
import com.prgrms.kdt.springbootbasic.exception.NoSuchResource;
import com.prgrms.kdt.springbootbasic.exception.ResourceDuplication;
import com.prgrms.kdt.springbootbasic.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }


    public Voucher saveVoucher(String voucherType, Long discountAmount){
        Voucher voucher = VoucherList.getVoucherType(voucherType).getConstructor(UUID.randomUUID(),discountAmount);
        if (checkDuplication(voucher)){
            throw new ResourceDuplication("동일한 Voucher가 이미 존재합니다");
        }

        Optional<Voucher> saveResult = voucherRepository.saveVoucher(voucher);
        if (saveResult.isEmpty())
            throw new JdbcQueryFail("Voucher 저장이 실패하였습니다");

        return saveResult.get();
    }


    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAllVouchers();
    }

    public boolean checkDuplication(Voucher voucher){
        var findResult = voucherRepository.findByTypeAndAmount(voucher.getVoucherType(), voucher.getDiscountAmount());
        if (findResult.isEmpty())
            return false;
        return true;
    }

    public Voucher updateVoucher(Voucher voucher){
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());
        if (foundVoucher.isEmpty()) //일치하는 객체가 없으면 update된 것이 없으므로 return empty
            throw new NoSuchResource("일치하는 Voucher가 없습니다");

        //수정할 내용 없으면 empty return
        if (foundVoucher.get().getDiscountAmount() == voucher.getDiscountAmount()
                && foundVoucher.get().getVoucherType().equals(voucher.getVoucherType()))
            return voucher;

        var updateResult = voucherRepository.updateVoucherAmount(voucher);
        if (updateResult.isEmpty())
            throw new JdbcQueryFail("Voucher Update가 실패하였습니다");

        return updateResult.get();
    }

    public boolean deleteVoucher(Voucher voucher){
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());
        if (foundVoucher.isEmpty())
            return true;
        return voucherRepository.deleteVoucher(voucher);
    }

}
