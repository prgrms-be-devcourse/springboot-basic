package org.prgrms.voucher.service;

import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {

        this.voucherRepository = voucherRepository;
    }

    public Voucher create(VoucherDto.VoucherRequest requestDto) {

        return null;
    }
}
