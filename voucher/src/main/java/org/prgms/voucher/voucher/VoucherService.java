package org.prgms.voucher.voucher;

import org.prgms.voucher.option.FixedAmount;
import org.prgms.voucher.option.PercentAmount;
import org.prgms.voucher.type.OptionType;
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
        if (voucherCreateDto.getOptionType() == OptionType.FIXED_AMOUNT) {
            Voucher voucher = new FixedAmount(
                    UUID.randomUUID(),
                    voucherCreateDto.getAmount());

        return voucherRepository.save(amountVoucher);
        } else if (voucherCreateDto.getOptionType() == OptionType.PERCENT_AMOUNT) {
            Voucher voucher = new PercentAmount(
                    UUID.randomUUID(),
                    voucherCreateDto.getAmount()
            );

            voucherRepository.save(voucher);
        }
        // exception
        }

    public List<AmountVoucher> listAmountVoucher() {
        return voucherRepository.findAll();
    }
}
