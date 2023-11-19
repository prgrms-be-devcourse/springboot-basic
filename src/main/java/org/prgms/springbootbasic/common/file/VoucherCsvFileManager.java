package org.prgms.springbootbasic.common.file;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.prgms.springbootbasic.common.CommonConstant.CSV_PATTERN;

@Component
@Slf4j
@Profile({"test"})
public class VoucherCsvFileManager {
    @Value("${basic.file.path}")
    private String FILE_PATH;
    private static final String CSV_FIRST_LINE = "UUID,Type,DiscountValue";
    private static final int UUID_IDX = 0;
    private static final int TYPE_IDX = 1;
    private static final int DISCOUNT_DEGREE_IDX = 2;

    private final CsvFileTemplate csvFileTemplate;

    public VoucherCsvFileManager(CsvFileTemplate csvFileTemplate) {
        this.csvFileTemplate = csvFileTemplate;
    }

    public List<Voucher> read(){
        return csvFileTemplate.read(FILE_PATH, this::convertToVoucher);
    }

    public void write(List<Voucher> voucherPolicies){
        csvFileTemplate.write(FILE_PATH, voucherPolicies, this::convertToString, CSV_FIRST_LINE);
    }

    private Voucher convertToVoucher(String line){
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

        VoucherPolicy voucherPolicy = thisVoucherType.create();
        UUID voucherId = UUID.fromString(splitLine.get(UUID_IDX));
        long discountDegree = Long.parseLong(splitLine.get(DISCOUNT_DEGREE_IDX));

        return new Voucher(voucherId, discountDegree, voucherPolicy);
    }

    private String convertToString(Voucher voucher){ // 외부에 도메인을 맞추면 안됨. -> DB 의존적 클래스랑 실제 내부 도메인 분리.
        // 이거를 위해서 VoucherPolicy가 getter를 들고있는게 말이 안됨. 얘를 도메인에 맞춰야지 얘때문에 도메인이 망가지면 안된다.
        StringBuilder sb = new StringBuilder();

        sb.append(voucher.getVoucherId());
        sb.append(",");
        sb.append(voucher.getVoucherPolicy().getClass().getSimpleName());
        sb.append(",");
        sb.append(voucher.getDiscountDegree());
        sb.append(System.lineSeparator());

        return sb.toString();
    }
}
