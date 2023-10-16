package devcourse.springbootbasic.util;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.exception.FileErrorMessage;
import devcourse.springbootbasic.exception.FileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class CsvFileHandler {

    private static final String CSV_LINE_TEMPLATE = "%s,%s,%d";
    private static final String CSV_DELIMITER = ",";
    private static final int CSV_FIELD_COUNT = 3;
    private final String filePath;

    public CsvFileHandler(String filePath) {
        validateFilePath(filePath);
        this.filePath = filePath;
    }

    private static void validateFilePath(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new FileException(FileErrorMessage.FILE_PATH_IS_NULL_OR_EMPTY);
        }
    }

    public List<Voucher> readVoucherListFromCsv() {
        List<Voucher> voucherList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) break;
                String[] parts = line.split(CSV_DELIMITER);
                if (parts.length != CSV_FIELD_COUNT) {
                    throw new FileException(FileErrorMessage.CSV_FIELD_COUNT_MISMATCH);
                }
                voucherList.add(parseVoucherFromCsvParts(parts));
            }
        } catch (IOException e) {
            throw new FileException(FileErrorMessage.IO_EXCEPTION);
        }

        return voucherList;
    }

    private Voucher parseVoucherFromCsvParts(String[] parts) {
        UUID voucherId = UUID.fromString(parts[0]);
        VoucherType voucherType = VoucherType.valueOf(parts[1]);
        long discountValue = getParseInputWithPrint(parts[2], Long::parseLong);

        return Voucher.createVoucher(voucherId, voucherType, discountValue);
    }

    private <T> T getParseInputWithPrint(String part, Function<String, T> parseFunction) {
        try {
            return parseFunction.apply(part);
        } catch (NumberFormatException e) {
            throw new FileException(FileErrorMessage.INVALID_NUMBER_FORMAT);
        }
    }

    public void writeVoucherListToCsv(List<Voucher> voucherList) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Voucher voucher : voucherList) {
                String csvLine = createCsvLine(voucher);
                bufferedWriter.write(csvLine);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new FileException(FileErrorMessage.IO_EXCEPTION);
        }
    }

    private String createCsvLine(Voucher voucher) {
        return String.format(CSV_LINE_TEMPLATE,
                voucher.getId(), voucher.getVoucherType(), voucher.getDiscountValue());
    }
}
