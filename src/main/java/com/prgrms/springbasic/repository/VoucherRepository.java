package com.prgrms.springbasic.repository;

import com.prgrms.springbasic.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VoucherRepository {

    Voucher saveVoucher(Voucher voucher);

    List<Voucher> findAll();
}
