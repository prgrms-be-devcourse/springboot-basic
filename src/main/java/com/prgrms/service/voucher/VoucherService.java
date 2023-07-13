package com.prgrms.service.voucher;

import com.prgrms.model.KeyGenerator;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherCreator;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.dto.voucher.VoucherRequest;
import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.dto.voucher.VoucherConverter;
import com.prgrms.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherConverter dtoConverter;
    private final KeyGenerator keyGenerator;
    private final VoucherCreator voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherConverter dtoConverter, KeyGenerator keyGenerator, VoucherCreator voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.dtoConverter = dtoConverter;
        this.keyGenerator = keyGenerator;
        this.voucherFactory = voucherFactory;
    }

    public Voucher createVoucher(VoucherRequest voucherRequest) {
        int id = keyGenerator.make();

        return voucherFactory.createVoucher(id, voucherRequest);
    }

    public List<VoucherResponse> getAllVoucherList() {
        Vouchers vouchers = voucherRepository.getAllVoucher();

        return dtoConverter.convertVoucherResponse(vouchers);
    }
}
