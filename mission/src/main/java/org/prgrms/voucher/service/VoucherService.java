package org.prgrms.voucher.service;

import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.prgrms.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {

        this.voucherRepository = voucherRepository;
    }

    public Voucher create(VoucherDto.VoucherRequest requestDto) {

        if (requestDto.voucherType() == null) {
            throw new IllegalArgumentException("VoucherType is null");
        }

        Voucher voucher = requestDto.toDomain();

        return voucherRepository.save(voucher);
    }

    public List<Voucher> list() {

        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchersByTypeAndTerm(String voucherType, LocalDate after, LocalDate before) {

        VoucherType type = VoucherType.findByUserInput(voucherType);
        return voucherRepository.findByTypeAndTerm(type, after, before);
    }

    public List<Voucher> getVouchersByTerm(LocalDate after, LocalDate before) {

        return voucherRepository.findByTerm(after, before);
    }

    public List<Voucher> getVouchersByType(String voucherType) {

        VoucherType type = VoucherType.findByUserInput(voucherType);
        return voucherRepository.findByType(type);
    }

    public Voucher getVoucherById(Long voucherId) {

        return voucherRepository.findById(voucherId).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteVoucherById(Long voucherId) {

        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(IllegalArgumentException::new);
        voucherRepository.deleteById(voucher.getVoucherId());
    }
}
