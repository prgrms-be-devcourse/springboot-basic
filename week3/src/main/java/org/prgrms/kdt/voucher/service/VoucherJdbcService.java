package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.IVoucherJdbcRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class VoucherJdbcService implements IVoucherJdbcService{

    private final IVoucherJdbcRepository voucherRepository;

    public VoucherJdbcService(IVoucherJdbcRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void create(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> list() {
        return voucherRepository.findAll();
    }

    @Override
    public void create(String voucherTypeStr, Long amount) {
        var voucherType = VoucherType.valueOf(voucherTypeStr);
        switch (voucherType){
            case PERCENT_DISCOUNT_VOUCHER -> voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), amount, voucherType, LocalDateTime.now()));
            case FIXED_AMOUNT_VOUCHER -> voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), amount, voucherType, LocalDateTime.now()));
        };
    }

    @Override
    public Optional<Voucher> getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    @Override
    public void deleteById(UUID voucherId) {
        voucherRepository.deleteByVoucherId(voucherId);
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return voucherRepository.findByVoucherType(voucherType);
    }

    @Override
    public List<Voucher> findByVouchersTerm(String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        return voucherRepository.findByVouchersTerm(startDate, endDate);
    }
}
