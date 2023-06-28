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
    private static final int INITIAL_MONEY = 100000;
    private static final String VOUCHER_TYPE = "바우처 종류";
    private static final String AFTER_DISCOUNT_VALUE = "할인가";
    private static final String PUBLISH_DATE = "발행일";
    private static final String EXPIRATION_DATE = "만료일";
    private static final String SEPARATOR = ": ";
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    void createVoucher(VoucherCreateDto voucherCreateDto) {
        if (voucherCreateDto.getOptionType() == OptionType.FIXED_AMOUNT) {
            Voucher voucher = new FixedAmount(
                    UUID.randomUUID(),
                    voucherCreateDto.getAmount());

            voucherRepository.save(voucher);
        } else if (voucherCreateDto.getOptionType() == OptionType.PERCENT_AMOUNT) {
            Voucher voucher = new PercentAmount(
                    UUID.randomUUID(),
                    voucherCreateDto.getAmount()
            );

            voucherRepository.save(voucher);
        }
        // exception
    }

    void listVoucher() {
        List<Voucher> vouchers = voucherRepository.findAll();

        StringBuilder sb = new StringBuilder();

        for (Voucher voucher : vouchers) {
            sb.append("\n");
            sb.append(VOUCHER_TYPE).append(SEPARATOR).append(voucher.getOptionType()).append("\n");
            sb.append(AFTER_DISCOUNT_VALUE).append(SEPARATOR).append(voucher.discount(INITIAL_MONEY)).append("\n");
            sb.append(PUBLISH_DATE).append(SEPARATOR).append(voucher.getPublishDate()).append("\n");
            sb.append(EXPIRATION_DATE).append(SEPARATOR).append(voucher.getExpirationDate()).append("\n");
        }

        System.out.print(sb);
    }
}
