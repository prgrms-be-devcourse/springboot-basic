package prgms.vouchermanagementapp.view;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.configuration.FileConfig;
import prgms.vouchermanagementapp.domain.VoucherRecord;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;

import java.util.Optional;

@Component
public class IoManager {

    private static final Logger log = LoggerFactory.getLogger(IOManager.class);

    private final Reader reader;
    private final Writer writer;
    private final FileManager fileManager;
    private final FileConfig fileConfig;

    public IoManager(Reader reader, Writer writer, FileManager fileManager, FileConfig fileConfig) {
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
        } catch (IllegalArgumentException illegalArgumentException) {
            writer.printException(illegalArgumentException);
            return Optional.empty();
        }
    }

    public Optional<Ratio> askFixedDiscountRatio() {
        writer.printFixedDiscountRatioGuide();
        long fixedDiscountRatio = readNumber();

        try {
            Ratio ratio = new Ratio(fixedDiscountRatio);
            return Optional.of(ratio);
        } catch (IllegalArgumentException illegalArgumentException) {
            writer.printException(illegalArgumentException);
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

    public String askCustomerName() {
        writer.printCustomerGuide();
        return reader.readLine();
    }
}
