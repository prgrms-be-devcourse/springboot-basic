package org.prgrms.orderApp.voucher;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class VoucherService {

    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }
    private UUID voucherId ;

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public Optional<Voucher> saveVoucher(Voucher voucher) throws IOException {
        return voucherRepository.insert(voucher) >0 ? Optional.of(voucher):Optional.empty();
    }

    public List<Voucher> getAllVouchers() throws IOException, ParseException {
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {

    }

}
