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
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private final YmlPropertiesLoader ymlPropertiesLoader;
    private final ResourceLoader resourceLoader;
    private File voucherCsvFile;

    public FileVoucherRepository(YmlPropertiesLoader ymlPropertiesLoader, ResourceLoader resourceLoader) {
        this.ymlPropertiesLoader = ymlPropertiesLoader;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> vouchers = this.getVouchers();
        return vouchers.stream().filter(voucher -> voucher.getId().equals(voucherId)).findFirst();
    }

    @Override
    public List<Voucher> getVouchers() {
        List<String[]> rows;

        logger.info("{}에서 Voucher 읽기 시작", this.voucherCsvFile.getPath());

        try {
            CSVReader csvReader = getCSVReader();
            rows = csvReader.readAll();
            csvReader.close();
        } catch (IOException | CsvException exception) {
            logger.error("{}에서 Voucher 정보 읽어 오기 실패", this.voucherCsvFile.getPath(), exception);
            throw new RuntimeException(exception);
        }

        logger.info("{}에서 Voucher 읽어 오기 종료", this.voucherCsvFile.getPath());
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
            logger.error("{}에서 Voucher 정보 저장하기 실패", this.voucherCsvFile.getPath(), ioException);
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

    private CSVWriter getCSVWriter() {
        FileWriter csvFileWriter;
        try {
            csvFileWriter = new FileWriter(this.voucherCsvFile, true);
        } catch (IOException ioException) {
            logger.error("voucher.csv 쓰기 실패", ioException);
            throw new RuntimeException();
        }
        return new CSVWriter(csvFileWriter);
    }

    private CSVReader getCSVReader() {
        FileReader csvFileReader;
        try {
            csvFileReader = new FileReader(this.voucherCsvFile);
        } catch (IOException ioException) {
            logger.error("voucher.csv 읽기 실패", ioException);
            throw new RuntimeException();
        }
        return new CSVReader(csvFileReader);
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        try {
            this.voucherCsvFile = this.resourceLoader
                    .getResource(this.ymlPropertiesLoader.getVoucherFilePath())
                    .getFile();
        } catch (IOException ioException) {
            logger.error("voucher.csv가 존재하지 않습니다.", ioException);
            throw new RuntimeException();
        }
    }


}
