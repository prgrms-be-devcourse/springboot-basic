package org.prgms.vouchermanagement.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.voucher.domain.dto.response.VoucherResponseDto;
import org.prgms.vouchermanagement.voucher.domain.dto.request.VoucherSaveRequestDto;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.domain.repository.VoucherRepository;
import org.prgms.vouchermanagement.voucher.validator.VoucherInputValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ThymeleafVoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherInputValidator validator;

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public Optional<VoucherResponseDto> findVoucherById(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        if (voucher.isPresent()) {
            VoucherResponseDto voucherDto = new VoucherResponseDto(
                    voucher.get().getVoucherId(),
                    voucher.get().getDiscount(),
                    voucher.get().getVoucherType()
            );
            return Optional.of(voucherDto);
        }
        return Optional.empty();
    }

    @Transactional
    public VoucherSaveRequestDto createNewVoucher(VoucherSaveRequestDto voucherDto) {
        Voucher newVoucher = new Voucher(UUID.randomUUID(), voucherDto.discount(), voucherDto.voucherType());
        validator.checkFixedOrPercentAmount(String.valueOf(newVoucher.getDiscount()), newVoucher.getVoucherType());
        voucherRepository.save(newVoucher);
        return voucherDto;
    }

    @Transactional
    public VoucherResponseDto updateVoucher(VoucherResponseDto voucherDto) {
        Voucher updateVoucher =  new Voucher(voucherDto.voucherId(), voucherDto.discount(), voucherDto.voucherType());
        validator.checkFixedOrPercentAmount(String.valueOf(updateVoucher.getDiscount()), updateVoucher.getVoucherType());
        voucherRepository.update(updateVoucher);
        return voucherDto;
    }

    @Transactional
    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

}
