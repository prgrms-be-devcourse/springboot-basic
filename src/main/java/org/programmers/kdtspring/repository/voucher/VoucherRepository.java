package org.programmers.kdtspring.repository.voucher;

import org.programmers.kdtspring.entity.voucher.Voucher;

import java.io.IOException;
import java.util.List;

public interface VoucherRepository {

    void save(Voucher voucher) throws IOException;

    List<Voucher> findAll() throws IOException;

}
