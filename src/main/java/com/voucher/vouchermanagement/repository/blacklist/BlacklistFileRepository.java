package com.voucher.vouchermanagement.repository.blacklist;

import com.voucher.vouchermanagement.model.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class BlacklistFileRepository implements BlacklistRepository {

    @Value("${db.path}")
    private String dbDirectory;
    @Value("${db.blacklist.name}")
    private String blacklistDbName;
    private final ResourceLoader resourceLoader;
    private static final Logger logger = LoggerFactory.getLogger(BlacklistFileRepository.class);

    public BlacklistFileRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Customer> findAll() {
        BufferedReader blacklistReader = getBufferedReader(dbDirectory, blacklistDbName);

        return readAllFromReader(blacklistReader)
                .stream().map(this::csvDeserialize)
                .collect(Collectors.toList());
    }

    private Customer csvDeserialize(String csvLine) {
        StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
        String id = stringTokenizer.nextToken().trim();
        String name = stringTokenizer.nextToken().trim();

        return new Customer(UUID.fromString(id), name);
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
