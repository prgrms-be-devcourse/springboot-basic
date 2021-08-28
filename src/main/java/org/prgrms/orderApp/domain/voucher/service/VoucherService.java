package org.prgrms.orderApp.domain.voucher.service;

import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.infrastructure.impl.TempVoucherRepository;
import org.prgrms.orderApp.presentation.commandOperator.util.ProcessingStatus;
import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.prgrms.orderApp.domain.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class VoucherService {

    //@Autowired
    private VoucherRepository voucherRepository;

    private Map<String,Object> processingResultsMessages = new HashMap<String, Object>();
    private ProcessingStatus status;
    private UUID voucherId ;

    public VoucherService(TempVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public Map<String, Object> saveVoucher(Voucher voucher) throws IOException {
        processingResultsMessages.clear();

        if (voucherRepository.save(voucher) >0 ){
            status = ProcessingStatus.SUCCESS;
            voucherId = voucher.getVoucherId();
        }else {
            status = ProcessingStatus.FAIL;
            voucherId = null;
        }
        processingResultsMessages.put("status", status);
        processingResultsMessages.put("voucherId", voucherId);
        return processingResultsMessages;

    }

    public List<Voucher> getAllVouchers() throws IOException, ParseException {
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {

    }

}
