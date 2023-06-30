package org.prgrms.vouchermission;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(String type, long value, LocalDate createdDate, LocalDate expirationDate) {

        try {
            VoucherFactory voucherFactory = VoucherFactory.valueOf(type);
            Voucher voucher = voucherFactory.createVoucher(value, createdDate, expirationDate);
            return voucherRepository.insert(voucher);

        } catch (IllegalStateException e) {
            throw new IllegalArgumentException();
        }
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

}
