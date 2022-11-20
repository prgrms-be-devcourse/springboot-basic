package prgms.vouchermanagementapp.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.configuration.FileConfig;
import prgms.vouchermanagementapp.domain.FileVoucherRecord;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.VoucherRecord;
import prgms.vouchermanagementapp.repository.util.VoucherContentsConverter;
import prgms.vouchermanagementapp.view.FileManager;

import java.io.File;

@Component
@Profile("release | test")
public class FileVouchers implements Vouchers {

    private static final String MESSAGE_FORMAT = "%-20s, %-20s";
    private static final String INITIAL_MESSAGE = String.format(MESSAGE_FORMAT, "Voucher Type", "Amount/Ratio");

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
        fileManager.writeContents(file, VoucherContentsConverter.toContents(voucher, MESSAGE_FORMAT));
    }
}
