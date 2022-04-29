package com.prgrms.kdt.springbootbasic.service;

import com.prgrms.kdt.springbootbasic.VoucherList;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
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

    public Voucher createVoucher(String voucherType, UUID voucherId, Long discountAmount){
        return VoucherList.getVoucherType(voucherType).getConstructor(voucherId,discountAmount);
    }

    public Optional<Voucher> saveVoucher(Voucher voucher){
        if (checkDuplication(voucher)){
            return Optional.empty();
        }
        return voucherRepository.saveVoucher(voucher);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAllVouchers();
    }

    public boolean checkDuplication(Voucher voucher){
        var findResult = voucherRepository.findById(voucher.getVoucherId());
        if (findResult.isEmpty())
            return false;
        return true;
    }

    public Optional<Voucher> updateVoucher(Voucher voucher){
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());
        if (foundVoucher.isEmpty()) //일치하는 객체가 없으면 update된 것이 없으므로 return empty
            return Optional.empty();

        //수정할 내용 없으면 empty return
        if (foundVoucher.get().getDiscountAmount() == voucher.getDiscountAmount())
            return Optional.empty();

        return voucherRepository.updateVoucherAmount(voucher);
    }

    public boolean deleteVoucher(Voucher voucher){
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());
        if (foundVoucher.isEmpty())
            return false;
        return voucherRepository.deleteVoucher(voucher);
    }

}
