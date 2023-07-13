package org.prgms.vouchermanagement.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.voucher.domain.dto.VoucherDto;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ThymeleafVoucherService {

    private final VoucherRepository voucherRepository;

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public Optional<VoucherDto> findVoucherById(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        if (voucher.isPresent()) {
            VoucherDto voucherDto = new VoucherDto();
            voucherDto.setVoucherId(voucher.get().getVoucherId());
            voucherDto.setDiscount(voucher.get().getDiscount());
            voucherDto.setVoucherType(voucher.get().getVoucherType());
            return Optional.of(voucherDto);
        }
        return Optional.empty();
    }

    @Transactional
    public VoucherDto createNewVoucher(VoucherDto voucherDto) {
        Voucher newVoucher = new Voucher(UUID.randomUUID(), voucherDto.getDiscount(), voucherDto.getVoucherType());
        voucherRepository.save(newVoucher);
        return voucherDto;
    }

    @Transactional
    public VoucherDto updateVoucher(VoucherDto voucherDto) {
        Voucher updateVoucher =  new Voucher(voucherDto.getVoucherId(), voucherDto.getDiscount(), voucherDto.getVoucherType());
        voucherRepository.update(updateVoucher);
        return voucherDto;
    }

    @Transactional
    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

}
