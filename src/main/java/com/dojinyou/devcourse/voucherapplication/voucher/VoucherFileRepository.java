package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.utils.CsvFileUtils;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import com.dojinyou.devcourse.voucherapplication.voucher.entity.VoucherEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class VoucherFileRepository implements VoucherRepository {
    private static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";
    private static final String ERROR_MESSAGE_NO_SUCH_FILE = "파일 경로를 확인하십시오.";
    private static final String FILE_NAME = "Voucher";
    private static final String FILE_EXTENSION = "csv";
    private static final AtomicLong idGenerator = new AtomicLong();

    private final Path FILE_PATH;
    private boolean isInitId = false;

    public VoucherFileRepository(@Value("${db-file-path}") String filePath) {
        FILE_PATH = Paths.get(filePath + "/" + FILE_NAME + "." + FILE_EXTENSION);
        if (!Files.exists(FILE_PATH)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NO_SUCH_FILE);
        }
        // TODO: refactoring 필요
        // ID값 초기화
        initializeId();
    }

    @Override
    public Voucher create(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
        }
        long id = idGenerator.incrementAndGet();
        VoucherEntity voucherEntity = VoucherMapper.domainToEntity(id, voucher);
        return save(voucherEntity);
    }

    private void initializeId() {
        try {
            String[] readData = CsvFileUtils.readLine(FILE_PATH, -1);
            int lastId = Integer.parseInt(readData[VoucherMapper.indexId]);
            idGenerator.set(lastId);
        } catch (Exception e) {
            idGenerator.set(0);
        }
    }

    @Override
    public List<Voucher> findAll() {
        // File 전체를 읽는다.
        List<String[]> readData = CsvFileUtils.read(FILE_PATH);

        // Value를 할당한 Voucher를 생성한다.
        List<Voucher> vouchers = readData.stream()
                                         .map(VoucherMapper::fileFormatToEntity)
                                         .map(VoucherMapper::entityToDomain)
                                         .collect(Collectors.toList());
        return vouchers;
    }

    private Voucher save(VoucherEntity voucherEntity) {
        String[] voucherFileFormat = VoucherMapper.entityToFileFormat(voucherEntity);
        // file에 voucher를 작성한다.
        CsvFileUtils.write(FILE_PATH, voucherFileFormat);
        // file를 읽어온다.
        String[] latestData = CsvFileUtils.readLine(FILE_PATH, -1);
        // 읽어온 데이터를 VoucherEntity로 변환시킨다.
        VoucherEntity savedVoucherEntity = VoucherMapper.fileFormatToEntity(latestData);
        return VoucherMapper.entityToDomain(savedVoucherEntity);
    }
}
