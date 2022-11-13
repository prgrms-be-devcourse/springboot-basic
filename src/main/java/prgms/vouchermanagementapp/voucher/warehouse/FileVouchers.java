package prgms.vouchermanagementapp.voucher.warehouse;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.configuration.FileConfig;
import prgms.vouchermanagementapp.voucher.model.FixedAmountVoucher;
import prgms.vouchermanagementapp.voucher.model.PercentDiscountVoucher;
import prgms.vouchermanagementapp.voucher.model.Voucher;
import prgms.vouchermanagementapp.voucher.warehouse.model.FileVoucherRecord;
import prgms.vouchermanagementapp.voucher.warehouse.model.VoucherRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
@Primary
@Profile("release")
public class FileVouchers implements VoucherWarehouse {

    private static final String MESSAGE_FORMAT = "%-20s, %-20s";
    private static final String INITIAL_MESSAGE = String.format(MESSAGE_FORMAT, "Voucher Type", "Amount/Ratio")
            + System.lineSeparator()
            + "-".repeat(40);

    private final FileConfig fileConfig;

    public FileVouchers(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
        System.out.println(fileConfig.getVoucherRecord());
    }

    @Override
    public void store(Voucher voucher) {
        File file = initializeFile();
        writeContents(file, toContents(voucher));
    }

    @Override
    public VoucherRecord getVoucherRecord() {
        return new FileVoucherRecord(fileConfig.getVoucherRecord());
    }

    private File initializeFile() {
        File file = new File(fileConfig.getVoucherRecord());
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("created");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeContents(file, INITIAL_MESSAGE);
        }
        return file;
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

    private void writeContents(File file, String contents) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append(contents).append(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
