package prgms.vouchermanagementapp.storage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.configuration.FileConfig;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.io.FileManager;
import prgms.vouchermanagementapp.storage.model.FileVoucherRecord;
import prgms.vouchermanagementapp.storage.model.VoucherRecord;

import java.io.File;

@Component
@Profile("file | database")
public class FileVouchers implements Vouchers {

    private static final String MESSAGE_FORMAT = "%-20s, %-20s";
    private static final String INITIAL_MESSAGE = String.format(MESSAGE_FORMAT, "Voucher Type", "Amount/Ratio");

    private final FileConfig fileConfig;
    private final FileManager fileManager;
    private final VoucherContentsConverter contentsConverter;

    public FileVouchers(FileConfig fileConfig, FileManager fileManager, VoucherContentsConverter contentsConverter) {
        this.fileConfig = fileConfig;
        this.fileManager = fileManager;
        this.contentsConverter = contentsConverter;
    }

    @Override
    public VoucherRecord getVoucherRecord() {
        return new FileVoucherRecord(fileConfig.getVoucherRecord());
    }

    @Override
    public void store(Voucher voucher) {
        File file = fileManager.initializeFileWithContents(fileConfig.getVoucherRecord(), INITIAL_MESSAGE);
        fileManager.writeContents(file, contentsConverter.toContents(voucher, MESSAGE_FORMAT));
    }
}
