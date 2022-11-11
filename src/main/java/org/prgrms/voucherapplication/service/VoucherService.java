package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.dto.ResponseVoucherList;
import org.prgrms.voucherapplication.entity.Voucher;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    public void create(Voucher voucher) {
    }

    public ResponseVoucherList getList() {
        return new ResponseVoucherList();
    }
}
