package org.prgrms.voucherprgrms.voucher;

import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;
    private final VoucherCreator voucherCreator;

    public VoucherService(@Qualifier("memory") VoucherRepository voucherRepository, VoucherCreator voucherCreator) {
        this.voucherRepository = voucherRepository;
        this.voucherCreator = voucherCreator;
    }

    /**
     * create Voucher
     */
    public Voucher createVoucher() throws IllegalArgumentException{
        Voucher voucher = voucherCreator.create();
        logger.info("CREATE Voucher({})", voucher.getVoucherId());
        return voucherRepository.insert(voucher);
    }


    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }


}
