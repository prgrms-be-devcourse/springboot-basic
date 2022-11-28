package com.programmers.voucher.service;

import com.programmers.voucher.domain.TypeOfVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(@Qualifier("Jdbc") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void createVoucher(String email, String type, long discount) {
        TypeOfVoucher typeOfVoucher = TypeOfVoucher.getType(type);
        TypeOfVoucher.validateVoucher(typeOfVoucher, discount);
        int expirationPeriodByType = TypeOfVoucher.getExpirationPeriod(typeOfVoucher);
        Voucher voucher = new Voucher(UUID.randomUUID(), typeOfVoucher, discount, LocalDateTime.now(), LocalDateTime.now().plusMonths(expirationPeriodByType), email);
        voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public List<Voucher> getVouchersByCustomerEmail(String customerEmail) {
        return voucherRepository.findByCustomerEmail(customerEmail);
    }

    @Override
    public List<Voucher> getVouchersByDate(String customerEmail, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDate.parse(date, formatter).atStartOfDay();
        return voucherRepository.findByDate(customerEmail, dateTime);
    }

    @Override
    public void update(UUID voucherId, long discount) {
        voucherRepository.update(voucherId, discount);
    }

    @Override
    public void deleteById(UUID voucherId) {
        voucherRepository.deleteByVoucherId(voucherId);
    }


}
