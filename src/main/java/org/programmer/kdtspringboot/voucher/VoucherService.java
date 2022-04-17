package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(String type, Long value) {
        //타입을 가져오고
        try{
            VoucherType voucherType = VoucherType.getVoucherType(type);
            Voucher voucher = voucherType.create(value);
            voucherRepository.saveVoucher(voucher);
        }catch (IllegalArgumentException e){
            logger.info("Voucher Type 잘못 입력 했습니다 {}", type);
        }

    }

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }
}
