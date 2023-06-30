package org.prgms.voucher.voucher;

import org.prgms.voucher.voucher.factory.VoucherFactory;
import org.prgms.voucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public AmountVoucher createAmountVoucher(VoucherCreateDto voucherCreateDto) {
        VoucherFactory voucherFactory = voucherCreateDto.getOptionType().getVoucherFactory();
        AmountVoucher amountVoucher = voucherFactory.createVoucher(UUID.randomUUID(), voucherCreateDto.getAmount());

        return voucherRepository.save(amountVoucher);
    }

    public List<AmountVoucher> listAmountVoucher() {
        return voucherRepository.findAll();
    }
}
