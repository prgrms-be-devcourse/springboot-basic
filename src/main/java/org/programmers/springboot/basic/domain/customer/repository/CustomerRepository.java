package org.programmers.springboot.basic.domain.customer.repository;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.config.AppConfig;
import org.programmers.springboot.basic.AppConstants;
import org.programmers.springboot.basic.domain.customer.dto.CsvCustomerDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerMapper;
import org.programmers.springboot.basic.util.exception.CSVFileIOFailureException;
import org.programmers.springboot.basic.util.manager.CSVFileManager;
import org.programmers.springboot.basic.util.properties.ExternalProperties;
import org.programmers.springboot.basic.util.properties.FileProperties;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CustomerRepository {

    private final CSVFileManager csvFileManager;
    private final CustomerMapper customerMapper;
    private final FileProperties fileProperties;
    private final ExternalProperties externalProperties;

    public CustomerRepository(CSVFileManager csvFileManager, CustomerMapper customerMapper, FileProperties fileProperties, ExternalProperties externalProperties) {
        this.csvFileManager = csvFileManager;
        this.customerMapper = customerMapper;
        this.fileProperties = fileProperties;
        this.externalProperties = externalProperties;
    }

    public File getFILE() {
        String filePath = getFilePath();
        return new File(filePath);
    }

    public List<Customer> findBlackList() {
        return getBlackListFromFile();
    }

    private List<Customer> getBlackListFromFile() {

        List<Customer> blackList = new ArrayList<>();
        String line;

        try {
            BufferedReader reader = this.csvFileManager.getBufferedReader(getFILE());

            while ((line = reader.readLine()) != null) {
                String[] token = line.split(AppConstants.CSV_SEPARATOR);
                CsvCustomerDto customerDto = customerMapper.deserialize(token);
                Customer customer = customerMapper.mapToCustomer(customerDto);
                blackList.add(customer);
            }
        } catch (FileNotFoundException e) {
            throw new CSVFileIOFailureException("Exception Occurred: No matching file found!");
        } catch (IOException e) {
            throw new CSVFileIOFailureException("Exception Occurred: Failed to read file!");
        }

        return blackList;
    }

    private String getFilePath() {

        String folderPath = this.fileProperties.getUserDir();
        String fileName = this.fileProperties.getNames().get("customer").getFileName();
        String resourcePath = this.fileProperties.getResources().getPath();
        String filePath = folderPath + resourcePath + fileName;

        if (AppConfig.isRunningFromJar()) {
            folderPath = externalProperties.getProjDir();
            resourcePath = externalProperties.getResourceDir();
            filePath =  folderPath + resourcePath + fileName;
        }

        return filePath;
    }
}
