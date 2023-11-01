package com.pgms.part1.domain.voucher.service;

import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.dto.VoucherWebCreateRequestDto;
import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.domain.voucher.repository.VoucherRepository;
import com.pgms.part1.exception.ErrorCode;
import com.pgms.part1.exception.VoucherApplicationException;
import com.pgms.part1.util.keygen.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public void isVoucherExist(Long id){
        voucherRepository.findVoucherById(id).orElseThrow(() -> new VoucherApplicationException(ErrorCode.VOUCHER_NOT_EXIST));
    }

    public VoucherResponseDto createVoucher(VoucherWebCreateRequestDto voucherCreateRequestDto) {
        Voucher voucher = Voucher.newVocher(keyGenerator.getKey(), voucherCreateRequestDto.getDiscount(),  voucherCreateRequestDto.getVoucherDiscountType());

        voucherRepository.add(voucher);
        log.info("Voucher {} added", voucher.getId());

        return new VoucherResponseDto(voucher.getId(), voucher.getDiscount(), voucher.getVoucherDiscountType());
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> listVoucher() {
        return voucherRepository.list().stream()
                .map(voucher -> new VoucherResponseDto(voucher.getId(), voucher.getDiscount(), voucher.getVoucherDiscountType()))
                .toList();
    }

    public void deleteVoucher(Long id){
        isVoucherExist(id);
        voucherRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public VoucherResponseDto getVoucherById(Long id) {
        Voucher voucher = voucherRepository.findVoucherById(id).orElseThrow(() -> new VoucherApplicationException(ErrorCode.VOUCHER_NOT_EXIST));
        return new VoucherResponseDto(voucher.getId(), voucher.getDiscount(), voucher.getVoucherDiscountType());
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> findVouchersByCreatedDate(String date, VoucherDiscountType type) {
        isValidDateForm(date);

        List<Voucher> vouchers = voucherRepository.findVoucherByFilter(date, type);
        return vouchers.stream().map(voucher -> new VoucherResponseDto(voucher.getId(), voucher.getDiscount(), voucher.getVoucherDiscountType()))
                .toList();
    }

    private void isValidDateForm(String date) {
        if(date != null && !date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$"))
            throw new VoucherApplicationException(ErrorCode.INVALID_INPUT_DATA);
    }
}
