package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.common.exception.NotFoundException;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherService {
    public static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";

    private VoucherRepository voucherRepository;

    public VoucherService(@Qualifier("voucherJdbcRepository") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
        }
        return voucherRepository.create(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher findById(long id) {
        if (id <= 0) {
            throw new NotFoundException();
        }
        return voucherRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void deleteById(long id) {
        if (id <= 0) {
            throw new NotFoundException();
        }

        voucherRepository.deleteById(id);
    }

    public List<Voucher> findAllByTypeAndDate(VoucherType type, LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null) {
            fromDate = LocalDate.of(0,1,1);
        }
        if (toDate == null) {
            toDate = LocalDate.of(9999,12,31);
        }
        if (fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException();
        }
        return voucherRepository.findByTypeAndDate(type, fromDate, toDate);
    }

    public List<Voucher> findAllByType(VoucherType type) {
        return voucherRepository.findByType(type);
    }

    public List<Voucher> findAllByDate(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null) {
            fromDate = LocalDate.of(0,1,1);
        }
        if (toDate == null) {
            toDate = LocalDate.of(9999,12,31);
        }
        if (fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException();
        }
        return voucherRepository.findAllByDate(fromDate, toDate);
    }
}
