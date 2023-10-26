package com.pgms.part1.domain.voucher.service;

import com.pgms.part1.domain.voucher.dto.VoucherCreateRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.entity.FixedAmountDiscountVoucher;
import com.pgms.part1.domain.voucher.entity.PercentDiscountVoucher;
import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.repository.VoucherRepository;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.util.keygen.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VoucherService {
    private final Logger log = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    private final KeyGenerator keyGenerator;

    public VoucherService(VoucherRepository voucherRepository, KeyGenerator keyGenerator) {
        this.voucherRepository = voucherRepository;
        this.keyGenerator = keyGenerator;
    }

    public void createFixedAmountVoucher(VoucherCreateRequestDto voucherCreateRequestDto) {
        FixedAmountDiscountVoucher fixedAmountDiscountVoucher = Voucher.newFixedAmountDiscountVoucher(keyGenerator.getKey(), voucherCreateRequestDto.discount());
        try{
            voucherRepository.add(fixedAmountDiscountVoucher);
            log.info("Voucher {} added", fixedAmountDiscountVoucher.getId());
        }
        catch(Exception e){
            log.info(e.getMessage());
        }
    }

    public void createPercentDiscountVoucher(VoucherCreateRequestDto voucherCreateRequestDto) {
        PercentDiscountVoucher percentDiscountVoucher = Voucher.newPercentDiscountVoucher(keyGenerator.getKey(), voucherCreateRequestDto.discount());
        try {
            voucherRepository.add(percentDiscountVoucher);
            log.info("Voucher {} added", percentDiscountVoucher.getId());
        }
        catch(Exception e){
            log.info(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> listVoucher() {
        return voucherRepository.list().stream()
                .map(voucher -> new VoucherResponseDto(voucher.getId(), voucher.getDiscount(), voucher.getVoucherDiscountType()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> listVouchersByWallets(List<Wallet> wallets) {
        return voucherRepository.findVoucherByWallets(wallets).stream()
                .map(voucher -> new VoucherResponseDto(voucher.getId(), voucher.getDiscount(), voucher.getVoucherDiscountType()))
                .collect(Collectors.toList());
    }

    public void deleteVoucher(Long id){
        voucherRepository.delete(id);
    }
}
