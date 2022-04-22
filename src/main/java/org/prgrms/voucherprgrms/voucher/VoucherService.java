package org.prgrms.voucherprgrms.voucher;

import org.prgrms.voucherprgrms.io.InputConsole;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.model.VoucherDTO;
import org.prgrms.voucherprgrms.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VoucherService {

    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;
    private final VoucherCreator voucherCreator;

    public VoucherService(@Qualifier("named") VoucherRepository voucherRepository, VoucherCreator voucherCreator, InputConsole inputConsole) {
        this.voucherRepository = voucherRepository;
        this.voucherCreator = voucherCreator;
    }

    /**
     * create Voucher
     */
    @Transactional
    public Voucher createVoucher(VoucherDTO voucherDTO) throws IllegalArgumentException, RuntimeException {
        Voucher voucher = voucherCreator.create(voucherDTO);
        logger.info("CREATE Voucher({})", voucher.getVoucherId());
        return voucherRepository.insert(voucher);
    }


    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }


}
