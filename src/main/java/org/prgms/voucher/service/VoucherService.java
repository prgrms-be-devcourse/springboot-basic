package org.prgms.voucher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.voucher.dto.VoucherResponseDto;
import org.prgms.voucher.repository.VoucherRepository;
import org.prgms.voucher.voucher.Voucher;
import org.prgms.voucher.voucher.VoucherFactory;
import org.prgms.voucher.voucher.VoucherPolicy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherResponseDto findVoucher(UUID id) {

        return voucherRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("id에 해당하는 바우처가 존재하지 않습니다. id: {}", id);
                    return new NoSuchElementException("id에 해당하는 바우처가 존재하지 않습니다.");
                });
    }

    public Voucher create(VoucherPolicy voucherPolicy, long amount) {
        Voucher voucher = voucherFactory.createVoucher(voucherPolicy, amount);
        log.debug("바우처 생성: {}", voucher);
        return this.voucherRepository.save(voucher);
    }

    public List<VoucherResponseDto> getVoucherList() {
        return this.voucherRepository.findAll();
    }
}
