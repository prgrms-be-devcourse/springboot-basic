package com.programmers.part1.order.voucher;

import com.programmers.part1.domain.voucher.FixedAmountVoucher;
import com.programmers.part1.domain.voucher.PercentAmountVoucher;
import com.programmers.part1.domain.voucher.Voucher;
import com.programmers.part1.domain.voucher.VoucherType;
import com.programmers.part1.exception.voucher.VoucherTypeMissingException;
import com.programmers.part1.order.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 추후 로직이 추가 될 것을 고려해
 * Controller와 Service 계층을 나누어 진행하였습니다.
 * **/

@Service
public class VoucherService {

    private final VoucherRepository<UUID, Voucher> voucherRepository;

    public VoucherService(VoucherRepository<UUID, Voucher> voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, int amount){

        if (voucherType == VoucherType.FIXED)
            return voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(),amount));
        else if (voucherType == VoucherType.PERCENT)
            return voucherRepository.save(new PercentAmountVoucher(UUID.randomUUID(),amount));
        else
            throw new VoucherTypeMissingException("Voucher Type이 잘못 입력되었습니다.");
    }

    List<Voucher> getVoucherByVoucherType(String voucherType){
        return voucherRepository.findVouchersByVoucherType(VoucherType.valueOf(voucherType));
    }


    public Optional<Voucher> getVoucherById(UUID voucherId){
        return voucherRepository.findById(voucherId);
    }

    public void deleteVoucherById(UUID voucherId){
        voucherRepository.deleteById(voucherId);
    }

    public List<Voucher> getAllVoucher(){
        return voucherRepository.findAll();
    }


    public List<Voucher> getVouchersWithCustomer(UUID customerId) {
        return voucherRepository.findVoucherByCustomerId(customerId);
    }
}
