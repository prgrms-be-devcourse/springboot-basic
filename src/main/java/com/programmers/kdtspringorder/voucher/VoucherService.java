package com.programmers.kdtspringorder.voucher;

import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.factory.VoucherFactory;
import com.programmers.kdtspringorder.voucher.repository.VoucherRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(@Qualifier("memory") VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {

    }

    public Voucher createVoucher(String voucherType) {
        Voucher voucher = voucherFactory.createVoucher(VoucherType.valueOf(Strings.toRootUpperCase(voucherType)));
        voucherRepository.save(voucher);
        return voucher;
    }

    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }
}
