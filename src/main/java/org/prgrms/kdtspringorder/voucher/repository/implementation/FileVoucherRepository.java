package org.prgrms.kdtspringorder.voucher.repository.implementation;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.prgrms.kdtspringorder.config.YmlPropertiesLoader;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdtspringorder.voucher.enums.VoucherPolicy;
import org.prgrms.kdtspringorder.voucher.repository.abstraction.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Profile("!dev")
@Primary
public class FileVoucherRepository implements VoucherRepository {

    public static final int COL_UUID = 0;
    public static final int COL_VOUCHER_TYPE = 1;
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final String filePath;

    public FileVoucherRepository(YmlPropertiesLoader ymlPropertiesLoader) {
        this.filePath = Paths.get(ymlPropertiesLoader.getVoucherFilePath()).toAbsolutePath().toString();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> vouchers = this.getVouchers();
        return vouchers.stream().filter(voucher -> voucher.getId().equals(voucherId)).findFirst();
    }

    @Override
    public List<Voucher> getVouchers() {
        List<String[]> rows;

        logger.info("{}에서 Voucher 읽기 시작", this.filePath);

        try {
            CSVReader csvReader = getCSVReader();
            rows = csvReader.readAll();
            csvReader.close();
        } catch (IOException | CsvException exception) {
            logger.error("{}에서 Voucher 정보 읽어 오기 실패", this.filePath, exception);
            throw new RuntimeException(exception);
        }

        logger.info("{}에서 Voucher 읽어 오기 종료", this.filePath);
        return rows
                .stream()
                .map(this::generateVoucherFrom)
                .collect(Collectors.toList());
    }

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        logger.info("Voucher 저장 시작");
        voucher.assignId(generateId());
        logger.info("새로운 Voucher에 ID 할당 : {}", voucher.getId());
        String[] newRow = generateRowFrom(voucher);

        try {
            CSVWriter csvWriter = getCSVWriter();
            csvWriter.writeNext(newRow);
            csvWriter.close();
        } catch (IOException ioException) {
            logger.error("{}에서 Voucher 정보 저장하기 실패", this.filePath, ioException);
            // 이렇게 하는게 맞을까요?
            throw new RuntimeException(ioException);
        }

        logger.info("Voucher 저장 완료");
        return voucher;
    }

    private UUID generateId() {
        return UUID.randomUUID();
    }

    private String[] generateRowFrom(Voucher voucher) {
        String[] row = new String[2];
        row[COL_UUID] = voucher.getId().toString();
        row[COL_VOUCHER_TYPE] = voucher.getVoucherTypeInString();
        return row;
    }

    private Voucher generateVoucherFrom(String[] row) {
        String voucherType = row[COL_VOUCHER_TYPE];
        VoucherPolicy voucherPolicy = VoucherPolicy.of(voucherType);

        String uuid = row[COL_UUID];
        UUID id = UUID.fromString(uuid);

        Voucher voucher = new Voucher(voucherPolicy);
        voucher.assignId(id);

        return voucher;
    }

    private CSVWriter getCSVWriter() throws IOException {
        return new CSVWriter(new FileWriter(this.filePath, true));
    }

    private CSVReader getCSVReader() throws IOException {
        return new CSVReader(new FileReader(this.filePath));
    }

}
