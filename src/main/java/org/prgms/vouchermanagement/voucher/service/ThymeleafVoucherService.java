package org.prgms.vouchermanagement.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.voucher.domain.dto.VoucherDto;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;

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

    public Optional<VoucherDto> findVoucherDtoById(UUID voucherId) {
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

    public Voucher createNewVoucher(VoucherDto voucherDto) {
        Voucher newVoucher = new Voucher(UUID.randomUUID(), voucherDto.getDiscount(), voucherDto.getVoucherType());
        return voucherRepository.save(newVoucher);
    }

    public Voucher updateVoucher(VoucherDto voucherDto) {
        Voucher updateVoucher =  new Voucher(voucherDto.getVoucherId(), voucherDto.getDiscount(), voucherDto.getVoucherType());
        return voucherRepository.update(updateVoucher);
    }

}
