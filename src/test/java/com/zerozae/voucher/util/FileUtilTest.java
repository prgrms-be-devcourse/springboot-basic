package com.zerozae.voucher.util;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FileUtilTest {

    private final String testFilePath = "test.csv";
    private final FileUtil fileUtil;
    private Customer normalCustomer;
    private Customer blacklistCustomer;
    private File testFile;

    FileUtilTest() {
        this.fileUtil = new FileUtil();
    }

    @BeforeEach
    void setUp() {
        normalCustomer = new Customer(UUID.randomUUID(), "normalUser", CustomerType.NORMAL);
        blacklistCustomer= new Customer(UUID.randomUUID(), "blackUser", CustomerType.BLACKLIST);

        testFile = new File(testFilePath);
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void cleanUp() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    @DisplayName("파일에 회원 저장 테스트")
    void saveCustomerToFile_success_Test() {
        // Given

        // When
        fileUtil.saveToCsvFile(getCustomerInfo(normalCustomer), testFilePath);

        // Then
        List<String> strings = fileUtil.loadFromCsvFile(testFilePath);
        assertTrue(strings.contains(getCustomerInfo(normalCustomer)));
    }

    @Test
    @DisplayName("파일 회원 삭제 테스트")
    void deleteCustomerFromFile_success_Test() {
        // Given
        fileUtil.saveToCsvFile(getCustomerInfo(normalCustomer), testFilePath);
        fileUtil.deleteFileDataById(normalCustomer.getCustomerId(), testFilePath);

        // When & Then
        List<String> strings = fileUtil.loadFromCsvFile(testFilePath);
        assertTrue(strings.isEmpty());
    }

    @Test
    @DisplayName("파일 회원 수정 테스트")
    void updateCustomerFromFile_success_Test() {
        // Given
        fileUtil.saveToCsvFile(getCustomerInfo(normalCustomer), testFilePath);

        // When
        fileUtil.updateFile(getCustomerInfo(blacklistCustomer),normalCustomer.getCustomerId(), testFilePath);

        // Then
        List<String> strings = fileUtil.loadFromCsvFile(testFilePath);
        assertTrue(strings.contains(getCustomerInfo(blacklistCustomer)));
    }

    @Test
    @DisplayName("파일 회원 load 테스트")
    void loadCustomer_success_Test() {
        // Given
        fileUtil.saveToCsvFile(getCustomerInfo(normalCustomer), testFilePath);
        fileUtil.saveToCsvFile(getCustomerInfo(blacklistCustomer), testFilePath);

        // When
        List<String> strings = fileUtil.loadFromCsvFile(testFilePath);

        // Then
        String compareString = getCustomerInfo(normalCustomer) + getCustomerInfo(blacklistCustomer);
        assertTrue(strings.contains(compareString));
    }

    @Test
    @DisplayName("파일 제거 후 재생성 테스트")
    void clearDataFile_success_Test() {
        // Given
        fileUtil.saveToCsvFile(getCustomerInfo(normalCustomer), testFilePath);
        fileUtil.saveToCsvFile(getCustomerInfo(blacklistCustomer), testFilePath);

        // When
        fileUtil.clearDataFile(testFilePath);

        // Then
        List<String> strings = fileUtil.loadFromCsvFile(testFilePath);
        assertTrue(strings.isEmpty());
    }

    private String getCustomerInfo(Customer customer) {
        String customerId = String.valueOf(customer.getCustomerId());
        String customerName = customer.getCustomerName();
        String customerType = String.valueOf(customer.getCustomerType());
        return String.join(",", customerId,customerName, customerType);
    }
}
