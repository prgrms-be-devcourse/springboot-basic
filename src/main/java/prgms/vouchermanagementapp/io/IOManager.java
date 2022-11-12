package prgms.vouchermanagementapp.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.warehouse.model.VoucherRecord;

import java.text.MessageFormat;
import java.util.Optional;

@Component
public class IOManager {

    private final Reader reader;
    private final Writer writer;

    @Autowired
    public IOManager(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public Optional<CommandType> askCommand() {
        writer.printCommandGuide();
        String command = reader.readLine();

        try {
            CommandType commandType = CommandType.of(command);
            return Optional.of(commandType);
        } catch (IllegalArgumentException exception) {
            notifyErrorOccurred(MessageFormat.format("command ''{0}'' is invalid!!!", command));
            return Optional.empty();
        }
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

    public void notifyVouchers(VoucherRecord voucherRecord) {
        writer.printVoucherRecord(voucherRecord);
    }

    public void notifyErrorOccurred(String errorMessage) {
        writer.printError(errorMessage);
    }
}
