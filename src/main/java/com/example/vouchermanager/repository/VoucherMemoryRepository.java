package com.example.vouchermanager.repository;

import com.example.vouchermanager.domain.Voucher;
import com.example.vouchermanager.message.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class VoucherMemoryRepository {

    List<Voucher> vouchers = new ArrayList<>();

    public void create(Voucher voucher) {
        log.info(LogMessage.REPOSITORY_CREATE_VOUCHER.getMessage(), voucher.toString());

        vouchers.add(voucher);
    }

    public List<Voucher> list() {
        log.info(LogMessage.REPOSITORY_LIST_LIST.getMessage(), vouchers);

        return vouchers;
    }
}
