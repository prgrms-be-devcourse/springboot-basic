package org.prgms.springbootbasic.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.common.file.CsvFileTemplate;
import org.prgms.springbootbasic.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.prgms.springbootbasic.common.Util.CSV_PATTERN;

@Component
@Slf4j
@Profile({"dev", "prod"})
public class CsvVoucherFileManager {
    private static final String FILE_PATH = "./src/main/resources/voucher.csv";
    private static final int UUID_IDX = 0;
    private static final int TYPE_IDX = 1;
    private static final int DISCOUNT_VALUE_IDX = 2;

    private final CsvFileTemplate<Voucher> csvFileTemplate;

    public CsvVoucherFileManager(CsvFileTemplate<Voucher> csvFileTemplate) {
        this.csvFileTemplate = csvFileTemplate;
    }

    public List<Voucher> read(){
        return csvFileTemplate.read(FILE_PATH, this::lineToVoucher);
    }

    public void write(List<Voucher> vouchers){
        csvFileTemplate.write(FILE_PATH, vouchers, this::voucherToString);
    }

    private Voucher lineToVoucher(String line){
        log.debug("line = {}", line);

        List<String> splitLine = Arrays.stream(line.split(CSV_PATTERN))
                .map(s -> s.replaceAll("\"", "")).toList();

        VoucherType[] voucherTypes = VoucherType.values();
        for (VoucherType type : voucherTypes) {
            String voucherType = type.getDisplayName();
            String curStringType = splitLine.get(TYPE_IDX);
            if (curStringType.equals(voucherType)) {
                log.info("This voucher type is {}", voucherType);

                return type.create(
                        UUID.fromString(splitLine.get(UUID_IDX)),
                        Long.parseLong(splitLine.get(DISCOUNT_VALUE_IDX))
                );
            }
        }

        log.error("Invalid voucher type.");
        throw new IllegalArgumentException("Invalid voucher type.");
    }

    private String voucherToString(Voucher voucher){
        StringBuilder sb = new StringBuilder();

        sb.append(voucher.getVoucherId());
        sb.append(",");
        sb.append(voucher.getClass().getSimpleName());
        sb.append(",");
        sb.append(voucher.getDiscountAmount());
        sb.append(System.lineSeparator());

        return sb.toString();
    }
}
