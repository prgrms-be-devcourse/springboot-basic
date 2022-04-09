package org.voucherProject.voucherProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.repository.VoucherRepository;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    @Override
    public Voucher getVoucher(UUID voucherId) throws IOException {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Can't find a voucher for {0}", voucherId)));
    }

    @Override
    public List<Voucher> findAll() throws IOException {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher save(Voucher voucher) throws IOException {
        voucherRepository.save(voucher);
        return voucher;
    }

}
