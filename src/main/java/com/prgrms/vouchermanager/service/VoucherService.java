package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.voucher.*;
import com.prgrms.vouchermanager.dto.VoucherRequest;
import com.prgrms.vouchermanager.message.LogMessage;
import com.prgrms.vouchermanager.repository.voucher.VoucherRepository;
import com.prgrms.vouchermanager.util.VoucherFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.prgrms.vouchermanager.dto.VoucherRequest.*;

@Service
@Slf4j
public class VoucherService {

    private final VoucherRepository voucherRepository;
    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
        log.info(LogMessage.CHECK_VOUCHER_REPOSITORY.getMessage(), voucherRepository.getClass());
    }

    public Voucher create(VoucherCreateRequest request) {
        Voucher voucher = VoucherFactory.create(VoucherType.of(request.voucherType()), request.discount()).get();
        log.info(LogMessage.VOUCHER_INFO.getMessage(), voucher);

        voucherRepository.create(voucher);
        return voucher;
    }

    public Voucher findById(UUID id) {
        return voucherRepository.findById(id);
    }
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher updateDiscount(UUID id, int discount) {
        Voucher voucher = voucherRepository.findById(id);
        Voucher updateVoucher = VoucherFactory.update(id,
                        voucher.getType(),
                        discount)
                        .get();
        return voucherRepository.updateDiscount(updateVoucher);
    }

    public int delete(UUID id) {
        return voucherRepository.delete(id);
    }

    public List<Voucher> findByCondition(VoucherFindByConditionRequest request) {
        String start = getYearAndMonth(request.startYear(), request.startMonth());
        String end = getYearAndMonth(request.endYear(), request.endMonth());
        VoucherType voucherType = VoucherType.of(request.voucherType());
        List<Voucher> byDate
                = voucherRepository.findByDate(start, end);
        log.info("byDate : " + byDate);
        if(voucherType == VoucherType.BOTH) return byDate;

        List<Voucher> byDateAndType =
                voucherRepository.findByDateAndVoucherType(voucherType, start, end);
        log.info("complete : " + byDateAndType.toString());
        return byDateAndType;
    }

    private String getYearAndMonth(int year, int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar date = Calendar.getInstance();
        date.clear();

        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        return format.format(date.getTime());
    }
}
