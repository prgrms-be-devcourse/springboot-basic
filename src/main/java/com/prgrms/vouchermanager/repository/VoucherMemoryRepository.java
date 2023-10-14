package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.message.LogMessage;
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
        List<Voucher> voucherList = vouchers
                .values()
                .stream()
                .toList();

        log.info(LogMessage.REPOSITORY_LIST_LIST.getMessage(), voucherList.getClass());

        return voucherList;
    }
}
