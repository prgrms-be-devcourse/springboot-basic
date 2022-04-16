package org.prgms.voucheradmin.domain.voucher.dao;

import java.io.IOException;
import java.util.List;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;

public interface VoucherRepository {
    Voucher create(Voucher voucher) throws IOException;

    List<Voucher> findAll() throws IOException;

    default Voucher update(){
        return null;
    }

}
