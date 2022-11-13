package prgms.vouchermanagementapp.voucher.warehouse;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.configuration.FileConfig;
import prgms.vouchermanagementapp.io.FileManager;
import prgms.vouchermanagementapp.voucher.model.FixedAmountVoucher;
import prgms.vouchermanagementapp.voucher.model.PercentDiscountVoucher;
import prgms.vouchermanagementapp.voucher.model.Voucher;
import prgms.vouchermanagementapp.voucher.warehouse.model.FileVoucherRecord;
import prgms.vouchermanagementapp.voucher.warehouse.model.VoucherRecord;

import java.io.File;

@Component
@Primary
@Profile("release")
public class FileVouchers implements VoucherWarehouse {

    private static final String MESSAGE_FORMAT = "%-20s, %-20s";
    private static final String INITIAL_MESSAGE = String.format(MESSAGE_FORMAT, "Voucher Type", "Amount/Ratio")
            + System.lineSeparator()
            + "-".repeat(40);

    private final FileConfig fileConfig;
    private final FileManager fileManager;

    public FileVouchers(FileConfig fileConfig, FileManager fileManager) {
        this.fileConfig = fileConfig;
        this.fileManager = fileManager;
    }

    @Override
    public VoucherRecord getVoucherRecord() {
        return new FileVoucherRecord(fileConfig.getVoucherRecord());
    }

    @Override
    public void store(Voucher voucher) {
        File file = fileManager.initializeFileWithContents(fileConfig.getVoucherRecord(), INITIAL_MESSAGE);
        fileManager.writeContents(file, toContents(voucher));
    }

    private String toContents(Voucher voucher) {
        if (voucher instanceof FixedAmountVoucher) {
            return toSpecificContents((FixedAmountVoucher) voucher);
        }
        return toSpecificContents((PercentDiscountVoucher) voucher);
    }

    private String toSpecificContents(FixedAmountVoucher voucher) {
        String voucherType = voucher.getClass().getSimpleName();
        long amount = voucher.getFixedDiscountAmount().getAmount();
        return String.format(MESSAGE_FORMAT, voucherType, amount);
    }

    private String toSpecificContents(PercentDiscountVoucher voucher) {
        String voucherType = voucher.getClass().getSimpleName();
        long ratio = voucher.getFixedDiscountRatio().getRatio();
        return String.format(MESSAGE_FORMAT, voucherType, ratio + "%");
    }
}
