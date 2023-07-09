package prgms.spring_week1.domain.voucher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.voucher.model.Voucher;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.io.Input;

import java.util.List;

@Service
public class VoucherService {
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void insertNewVoucher(VoucherType voucherType, int discountAmount) {
        if(voucherType == VoucherType.PERCENT && discountAmount > 100){
            logger.warn("100 퍼센트 이상의 값은 넣을 수 없습니다.");
            throw new NumberFormatException();
        }

        Voucher voucher = new Voucher(voucherType, discountAmount);
        voucherRepository.insert(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

}
