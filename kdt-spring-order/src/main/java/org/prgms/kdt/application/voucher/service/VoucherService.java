package org.prgms.kdt.application.voucher.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.kdt.application.customer.domain.Customer;
import org.prgms.kdt.application.voucher.domain.FixedAmountVoucher;
import org.prgms.kdt.application.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.domain.VoucherType;
import org.prgms.kdt.application.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public Voucher createVoucher (VoucherType voucherType, long discountValue, Customer customer) {
        Voucher voucher = null;
        UUID customerId = customer != null ? customer.getCustomerId() : null;
        switch (voucherType) {
            case FIXED_AMOUNT:
                voucher = new FixedAmountVoucher(UUID.randomUUID(), customerId, discountValue, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                log.info("create FixedAmountVoucher {}", voucher);
                break;
            case PERCENT_DISCOUNT:
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), customerId, discountValue, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                log.info("create PercentDiscountVoucher {}", voucher);
                break;
            default:
                break;
        }
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> getAllVouchers () {
        List<Voucher> voucherList = voucherRepository.findAll();
        if (voucherList.isEmpty()) {
            throw new IllegalStateException("no such data");
        }
        return voucherList;
    }

    public Optional<Voucher> getVoucherByVoucherId(UUID voucherId) {
        return voucherRepository.findByVoucherId(voucherId);
    }

    public List<Voucher> getVoucherByCustomerId(UUID customerId) {
        List<Voucher> voucherList = voucherRepository.findByCustomerId(customerId);
        if (voucherList.isEmpty()) {
            throw new IllegalStateException("no such data");
        }
        return voucherList;
    }

    public Voucher updateVoucher(Voucher voucher, long discountValue) {
        voucher.changeDiscountValue(discountValue);
        return voucherRepository.updateDiscountValue(voucher);
    }

    public void deleteVoucherId(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
