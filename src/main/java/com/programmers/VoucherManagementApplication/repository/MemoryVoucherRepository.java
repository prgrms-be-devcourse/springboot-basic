package com.programmers.VoucherManagementApplication.repository;

import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

//ocp 구조 개방 폐쇄 원칙 ( 메모리 vs 파일 )
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private final List<Voucher> vouchers = new ArrayList<>();

    @Override
    public void addVoucher(Voucher voucher) {
        vouchers.add(voucher);
        logger.info("Add voucher => {}", voucher);
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("Call memory repository findAll method => {} ", vouchers);
        return vouchers;
    }

}
