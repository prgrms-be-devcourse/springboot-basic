package com.example.vouchermanager.repository;

import com.example.vouchermanager.domain.Voucher;
import com.example.vouchermanager.message.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class VoucherMemoryRepository {

    Map<UUID, Voucher> vouchers = new HashMap<>();

    public void create(Voucher voucher) {
        log.info(LogMessage.REPOSITORY_CREATE_VOUCHER.getMessage(), voucher.toString());

        vouchers.put(voucher.getId(), voucher);
    }

    public List<Voucher> list() {
        log.info(LogMessage.REPOSITORY_LIST_LIST.getMessage(), vouchers);

        return vouchers
                .values()
                .stream()
                .toList();
    }
}
