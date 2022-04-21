package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.utils.deserializer.CsvMapper;
import com.voucher.vouchermanagement.utils.deserializer.VoucherCsvMapper;
import com.voucher.vouchermanagement.utils.file.FileIOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Primary
public class VoucherFileRepository implements VoucherRepository {
    private final Resource voucherDb;
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);
    private final CsvMapper<Voucher> csvMapper = new VoucherCsvMapper();

    public VoucherFileRepository(@Value("${db.path}") String dbDirectory, @Value("${db.voucher.name}") String voucherDbName, ResourceLoader resourceLoader) {
        voucherDb = resourceLoader.getResource(dbDirectory + voucherDbName);
    }

    @Override
    public void insert(Voucher voucher) {
        try {
            String voucherCsvData = this.csvMapper.serialize(voucher);
            FileIOUtils.writeln(this.voucherDb.getFile(), voucherCsvData, true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return FileIOUtils.readAllLine(voucherDb.getFile())
                    .stream()
                    .map(csvMapper::deserialize)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }

        return Collections.emptyList();
    }

    public void deleteAll() {
        try {
            FileIOUtils.writeln(voucherDb.getFile(), null, false);
        } catch (IOException e) {
            this.logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
