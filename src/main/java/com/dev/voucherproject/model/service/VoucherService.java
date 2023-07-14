package com.dev.voucherproject.model.service;


import com.dev.voucherproject.controller.web.request.VoucherCreateRequest;
import com.dev.voucherproject.model.storage.voucher.VoucherDao;
import com.dev.voucherproject.model.voucher.Voucher;
import com.dev.voucherproject.model.voucher.VoucherDto;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VoucherService {
    private final VoucherDao voucherDao;

    public VoucherService(VoucherDao voucherDao) {
        this.voucherDao = voucherDao;
    }

    @Transactional
    public void insert(VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = Voucher.of(
                UUID.randomUUID(),
                LocalDateTime.now(),
                voucherCreateRequest.voucherPolicy(),
                voucherCreateRequest.discountFigure()
        );
        voucherDao.insert(voucher);
    }

    public List<VoucherDto> findAllVouchers() {
        return voucherDao.findAll().stream()
                .map(VoucherDto::fromEntity)
                .toList();
    }

    public List<VoucherDto> findAllVouchersByPolicy(VoucherPolicy voucherPolicy) {
        return voucherDao.findAllByPolicy(voucherPolicy).stream()
                .map(VoucherDto::fromEntity)
                .toList();
    }

    public List<VoucherDto> findAllBetweenDates(LocalDate startDate, LocalDate endDate) {
        return voucherDao.findAllBetweenDates(startDate, endDate).stream()
                .map(VoucherDto::fromEntity)
                .toList();
    }

    public VoucherDto findById(String id) {
        return voucherDao.findById(UUID.fromString(id)).map(VoucherDto::fromEntity).get();
    }

    @Transactional
    public void deleteById(String id) {
        voucherDao.deleteById(UUID.fromString(id));
    }
}
