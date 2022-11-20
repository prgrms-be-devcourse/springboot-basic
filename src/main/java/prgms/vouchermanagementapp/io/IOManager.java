package prgms.vouchermanagementapp.io;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.configuration.FileConfig;
import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.warehouse.model.VoucherRecord;

import java.util.Optional;

@Component
public class IOManager {

    private static final Logger log = LoggerFactory.getLogger(IOManager.class);

    private final Reader reader;
    private final Writer writer;
    private final FileManager fileManager;
    private final FileConfig fileConfig;

    public IOManager(Reader reader, Writer writer, FileManager fileManager, FileConfig fileConfig) {
        this.reader = reader;
        this.writer = writer;
        this.fileManager = fileManager;
        this.fileConfig = fileConfig;
    }

    public String askCommand() {
        writer.printCommandGuide();
        return reader.readLine();
    }

    public String askVoucherTypeIndex() {
        writer.printVoucherTypeGuide();
        return reader.readLine();
    }

    public Optional<Amount> askFixedDiscountAmount() {
        writer.printFixedAmountGuide();
        long number = readNumber();

        try {
            Amount amount = new Amount(number);
            return Optional.of(amount);
        } catch (IllegalArgumentException exception) {
            log.warn("Amount Creation Error: {} ", exception.getMessage());
            writer.printException(exception);
            return Optional.empty();
        }
    }

    public Optional<Ratio> askFixedDiscountRatio() {
        writer.printFixedDiscountRatioGuide();
        long fixedDiscountRatio = readNumber();

        try {
            Ratio ratio = new Ratio(fixedDiscountRatio);
            return Optional.of(ratio);
        } catch (IllegalArgumentException exception) {
            log.warn("Ratio Creation Error: {} ", exception.getMessage());
            writer.printException(exception);
            return Optional.empty();
        }
    }

    private long readNumber() {
        String inputNumber = reader.readLine();
        return Long.parseLong(inputNumber);
    }

    public void notifyExit() {
        writer.printExitMessage();
    }

    public void showVoucherRecord(VoucherRecord voucherRecord) {
        writer.printVoucherRecord(voucherRecord);
    }

    public void notifyErrorOccurred(String errorMessage) {
        writer.printError(errorMessage);
    }

    public void showBlacklist() {
        writer.printFileContents(fileManager.getFileByPath(fileConfig.getCustomerBlacklist()));
    }
}
