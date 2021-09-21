package org.prgrms.kdtbespring.voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private VoucherRepository voucherRepository;

    public VoucherService(@Qualifier("file") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }


    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a Voucher for {0}", voucherId)));
    }

    // 바우처 사용했을 때
    public void useVoucher(Voucher voucher) {
    }

    // Voucher 타입 생성
    public Optional<Voucher> create(VoucherType voucherType) {
        UUID voucherId = UUID.randomUUID();
        long value = 10L;

        Voucher voucher = voucherType.voucherCreate(voucherRepository, voucherId, value);
        voucherRepository.insert(voucher);
        return Optional.ofNullable(voucher);

    }

    // 리스트 반환
    public List<Voucher> list() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers;
    }
}
