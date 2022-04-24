package org.prgrms.voucher.service;

import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

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

        return null;
    }
}
