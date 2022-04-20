package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.service.CreateVoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile("prod")
public class VoucherFileRepository implements VoucherRepository {

    @Value("${db.path}")
    private String dbDirectory;
    @Value("${db.voucher.name}")
    private String voucherDbName;
    private final ResourceLoader resourceLoader;
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);

    public VoucherFileRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void insert(Voucher voucher) {
        BufferedWriter voucherWriter = getBufferedWriter(this.dbDirectory, this.voucherDbName);
        try {
            voucherWriter.write(csvSerialize(voucher));
            voucherWriter.newLine();
            voucherWriter.close();
        } catch (IOException e) {
            logAndPrintException(e);
        }
    }

    @Override
    public List<Voucher> findAll() {
        BufferedReader voucherReader = getBufferedReader(this.dbDirectory, this.voucherDbName);

        return readAllFromReader(voucherReader)
                .stream()
                .map(this::csvDeserialize)
                .collect(Collectors.toList());
    }

    public void deleteAll() {
        BufferedWriter voucherWriter = getBufferedWriter(this.dbDirectory, this.voucherDbName, false);
        try {
            voucherWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String csvSerialize(Voucher voucher) {
        return String.join(",", voucher.getClass().getSimpleName(),
                voucher.getVoucherId().toString(),
                voucher.getValue().toString(),
                voucher.getCreatedAt().toString());
    }

    private Voucher csvDeserialize(String csvLine) {
        StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
        VoucherType voucherType = VoucherType.getVoucherTypeByName(stringTokenizer.nextToken().trim());
        UUID id = UUID.fromString(stringTokenizer.nextToken().trim());
        long value = Long.parseLong(stringTokenizer.nextToken().trim());
        LocalDateTime createdAt = LocalDateTime.parse(stringTokenizer.nextToken());

        return voucherType.create(new CreateVoucherDto(id, value, createdAt));
    }

    private void logAndPrintException(Exception e) {
        this.logger.error(e.getMessage());
        System.out.println(e.getMessage());
    }

    private BufferedReader getBufferedReader(String directory, String target) {
        try {
            File db = this.resourceLoader.getResource(directory + target).getFile();
            FileReader fileReader = new FileReader(db);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            return bufferedReader;
        } catch (IOException e) {
            logAndPrintException(e);
        }

        return (BufferedReader) BufferedReader.nullReader();
    }

    private BufferedWriter getBufferedWriter(String directory, String target) {
        try {
            File db = this.resourceLoader.getResource(directory + target).getFile();
            FileWriter fileWriter = new FileWriter(db, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            return bufferedWriter;
        } catch (IOException e) {
            logAndPrintException(e);
        }

        return (BufferedWriter) BufferedWriter.nullWriter();
    }

    private List<String> readAllFromReader(BufferedReader bufferedReader) {
        List<String> list = new ArrayList<>();
        String buffer = "";
        try {
            while ((buffer = bufferedReader.readLine()) != null) {
                list.add(buffer);
            }
            bufferedReader.close();
        } catch (IOException e) {
            logAndPrintException(e);
        }

        return list;
    }
}
