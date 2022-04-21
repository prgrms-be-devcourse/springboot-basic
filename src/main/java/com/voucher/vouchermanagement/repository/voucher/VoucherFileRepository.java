package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.utils.deserializer.CsvDeserializer;
import com.voucher.vouchermanagement.utils.deserializer.VoucherDeserializer;
import com.voucher.vouchermanagement.utils.io.file.FileInput;
import com.voucher.vouchermanagement.utils.io.file.FileOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VoucherFileRepository implements VoucherRepository {

    private final Resource voucherDb;
    private final FileInput fileInput = new FileInput();
    private final FileOutput fileOutput = new FileOutput();
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);
    private static final CsvDeserializer<Voucher> csvDeserializer = new VoucherDeserializer();

    public VoucherFileRepository(@Value("${db.path}") String dbDirectory, @Value("${db.voucher.name}") String voucherDbName, ResourceLoader resourceLoader) {
        voucherDb = resourceLoader.getResource(dbDirectory + voucherDbName);
    }

    @Override
    public void insert(Voucher voucher) {
        try {
            String voucherCsvData = String.join(",", voucher.getClass().getSimpleName(),
                    voucher.getVoucherId().toString(),
                    voucher.getValue().toString(),
                    voucher.getCreatedAt().toString());

            this.fileOutput.writeln(voucherDb.getFile(), voucherCsvData, true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return this.fileInput.readAllLine(voucherDb.getFile())
                    .stream()
                    .map(csvDeserializer::deserialize)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }

        return Collections.emptyList();
    }

    public void deleteAll() {
        try {
            this.fileOutput.writeln(voucherDb.getFile(), null, false);
        } catch (IOException e) {
            this.logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
