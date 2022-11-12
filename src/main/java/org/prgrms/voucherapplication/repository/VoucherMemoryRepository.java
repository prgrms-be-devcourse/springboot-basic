package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.dto.ResponseVoucherList;
import org.prgrms.voucherapplication.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Profile("dev")
@Repository
public class VoucherMemoryRepository implements VoucherRepository{

    private ResponseVoucherList responseVoucherList;

    @Override
    public void save(Voucher voucher) {
        if (responseVoucherList == null) {
            responseVoucherList = new ResponseVoucherList(new ArrayList<>());
        }

        responseVoucherList.add(voucher);
    }

    @Override
    public String findAll() {
        return responseVoucherList.findAll();
    }
}
