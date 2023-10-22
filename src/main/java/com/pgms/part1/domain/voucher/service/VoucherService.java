package com.pgms.part1.domain.voucher.service;

import com.pgms.part1.domain.voucher.dto.VoucherCreateRequestDto;
import com.pgms.part1.domain.voucher.entity.FixedAmountDiscountVoucher;
import com.pgms.part1.domain.voucher.entity.PercentDiscountVoucher;
import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final Logger log = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createFixedAmountVoucher(VoucherCreateRequestDto voucherCreateRequestDto) {
        FixedAmountDiscountVoucher fixedAmountDiscountVoucher = Voucher.newFixedAmountDiscountVoucher(voucherCreateRequestDto.discount());
        try{
            voucherRepository.add(fixedAmountDiscountVoucher);
            log.info("Voucher {} added", fixedAmountDiscountVoucher.getId());
        }
        catch(Exception e){
            log.info(e.getMessage());
        }
    }

    public void createPercentDiscountVoucher(VoucherCreateRequestDto voucherCreateRequestDto) {
        PercentDiscountVoucher percentDiscountVoucher = Voucher.newPercentDiscountVoucher(voucherCreateRequestDto.discount());
        try {
            voucherRepository.add(percentDiscountVoucher);
            log.info("Voucher {} added", percentDiscountVoucher.getId());
        }
        catch(Exception e){
            log.info(e.getMessage());
        }
    }

    public List<Voucher> listVoucher() {
        return voucherRepository.list();
    }
}
