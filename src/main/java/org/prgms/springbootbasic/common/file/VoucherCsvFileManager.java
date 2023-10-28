package org.prgms.springbootbasic.common.file;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.prgms.springbootbasic.common.CommonConstant.CSV_PATTERN;

@Component
@Slf4j
@Profile({"dev", "prod"})
public class VoucherCsvFileManager { // CsvFileManager 하나로 합쳐서. domain은 최대한 순수하게 유지. 외부 의존성이 들어간다? 이게 도메인에 들어가면 변경이 취약. -> 분리
    private static final String FILE_PATH = "./src/main/resources/voucher.csv";
    private static final String CSV_FIRST_LINE = "UUID,Type,DiscountValue";
    private static final int UUID_IDX = 0;
    private static final int TYPE_IDX = 1;
    private static final int DISCOUNT_VALUE_IDX = 2;

    private final CsvFileTemplate csvFileTemplate;

    public VoucherCsvFileManager(CsvFileTemplate csvFileTemplate) {
        this.csvFileTemplate = csvFileTemplate;
    }

    public List<VoucherPolicy> read(){
        return csvFileTemplate.read(FILE_PATH, this::covertToVoucher);
    }

    public void write(List<VoucherPolicy> voucherPolicies){
        csvFileTemplate.write(FILE_PATH, voucherPolicies, this::convertToString, CSV_FIRST_LINE);
    }

    private VoucherPolicy covertToVoucher(String line){
        log.debug("line = {}", line);

        List<String> splitLine = Arrays.stream(line.split(CSV_PATTERN))
                .map(s -> s.replaceAll("\"", ""))
                .toList();
        VoucherType[] voucherTypes = VoucherType.values();

        VoucherType thisVoucherType =
                Arrays.stream(voucherTypes)
                        .filter(type -> type.getDisplayName().equals(splitLine.get(TYPE_IDX)))
                        .findAny()
                        .orElseThrow(() -> {
                            log.error("Invalid voucher type.");
                            return new IllegalArgumentException("Invalid voucher type");
                        });
        return thisVoucherType.create(UUID.fromString(splitLine.get(UUID_IDX)),
                Long.parseLong(splitLine.get(DISCOUNT_VALUE_IDX)));
    }

    private String convertToString(VoucherPolicy voucherPolicy){
        StringBuilder sb = new StringBuilder();

        sb.append(voucherPolicy.getVoucherId());
        sb.append(",");
        sb.append(voucherPolicy.getClass().getSimpleName());
        sb.append(",");
        sb.append(voucherPolicy.getDiscountAmount());
        sb.append(System.lineSeparator());

        return sb.toString();
    }
}
