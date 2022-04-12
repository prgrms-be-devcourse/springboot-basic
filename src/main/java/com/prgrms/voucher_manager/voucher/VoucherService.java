package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.exception.EmptyVoucherException;
import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void getFindAllVoucher(){
        try{
            voucherRepository.findAll();
        }catch (EmptyVoucherException e){
            logger.info("{}", e.getMessage());
        }
    }

    public void createVoucher(String type, Long value){
        try{
            VoucherType voucherType = VoucherType.getVoucherType(type);
            voucherRepository.save(voucherType.create(value));
        }catch (IllegalArgumentException e){
            logger.info("Voucher Type을 잘못 입력 했습니다 {}", type);
        }catch (WrongVoucherValueException we){
            logger.info("범위가 다릅니다. {}",we.getMessage());
        }
    }


    public Optional<Voucher> createFixedAmountVoucher(long amount){
        if(amount < 0){
            logger.warn("잘못된 amount 값을 입력 {}",amount);
            return Optional.empty();
        }
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        voucherRepository.save(voucher);
        return Optional.of(voucher);
    }

    public Optional<Voucher> createPercentDiscountVoucher(long percent){
        if(percent > 100){
            logger.warn("잘못된 percent 값을 입력 {}",percent);
            return Optional.empty();
        }
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        voucherRepository.save(voucher);
        return Optional.of(voucher);
    }





}
