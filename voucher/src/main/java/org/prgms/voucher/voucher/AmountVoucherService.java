package org.prgms.voucher.voucher;

import org.prgms.voucher.voucher.factory.AmountVoucherFactory;
import org.prgms.voucher.voucher.repository.AmountVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AmountVoucherService {
    private final AmountVoucherRepository amountVoucherRepository;

    public AmountVoucherService(AmountVoucherRepository amountVoucherRepository) {
        this.amountVoucherRepository = amountVoucherRepository;
    }

    public AmountVoucher createAmountVoucher(AmountVoucherCreateDto amountVoucherCreateDto) {
        AmountVoucherFactory amountVoucherFactory = amountVoucherCreateDto.getOptionType().getVoucherFactory();
        AmountVoucher amountVoucher = amountVoucherFactory.createVoucher(UUID.randomUUID(), amountVoucherCreateDto.getAmount());

        return amountVoucherRepository.save(amountVoucher);
    }

    public List<AmountVoucher> listAmountVoucher() {
        return amountVoucherRepository.findAll();
    }
}
