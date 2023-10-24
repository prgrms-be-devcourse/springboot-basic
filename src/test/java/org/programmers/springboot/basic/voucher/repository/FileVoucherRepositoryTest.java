package org.programmers.springboot.basic.voucher.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.programmers.springboot.basic.AppConstants;
import org.programmers.springboot.basic.config.VoucherConfig;
import org.programmers.springboot.basic.domain.voucher.dto.CsvVoucherDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.entity.FixedAmountVoucher;
import org.programmers.springboot.basic.domain.voucher.entity.PercentDiscountVoucher;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherValidatorNotFoundException;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherMapper;
import org.programmers.springboot.basic.domain.voucher.repository.FileVoucherRepository;
import org.programmers.springboot.basic.domain.voucher.repository.VoucherRepository;
import org.programmers.springboot.basic.domain.voucher.service.VoucherService;
import org.programmers.springboot.basic.domain.voucher.service.validate.FixedAmountVoucherValidator;
import org.programmers.springboot.basic.domain.voucher.service.validate.PercentDiscountVoucherValidator;
import org.programmers.springboot.basic.domain.voucher.service.validate.ValidateHandler;
import org.programmers.springboot.basic.util.exception.CSVFileIOFailureException;
import org.programmers.springboot.basic.util.manager.CSVFileManager;
import org.programmers.springboot.basic.util.properties.ExternalProperties;
import org.programmers.springboot.basic.util.properties.FileProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ComponentScan(
        basePackages = {"org.programmers.springboot.basic"},
        basePackageClasses = FileVoucherRepositoryTest.class
)
@ContextConfiguration(classes = {
        CSVFileManager.class,
        VoucherMapper.class,
        VoucherConfig.class,
        FileVoucherRepository.class,
        PercentDiscountVoucherValidator.class,
        FixedAmountVoucherValidator.class,
        FileProperties.class,
        FileVoucherRepositoryTest.class
})
@EnableConfigurationProperties(value = FileProperties.class)
@TestPropertySource("classpath:application.yaml")
@SpringBootTest
public class FileVoucherRepositoryTest {

    private final TestFileVoucherRepository testFileVoucherRepository;
    private final TestVoucherService testVoucherService;

    @Autowired
    public FileVoucherRepositoryTest(TestFileVoucherRepository testFileVoucherRepository, TestVoucherService testVoucherService) {
        this.testFileVoucherRepository = testFileVoucherRepository;
        this.testVoucherService = testVoucherService;
    }

    @AfterEach
    public void after() {
        try {
            String filePath = testFileVoucherRepository.getTestFilePath();
            Path path = Path.of(filePath);
            if (Files.exists(path)) Files.write(path, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Component("testVoucherService")
    static class TestVoucherService extends VoucherService {

        private final VoucherRepository voucherRepository;
        private final VoucherMapper voucherMapper;
        private final Map<VoucherType, ValidateHandler> validateHandlers;

        public TestVoucherService(Map<VoucherType, ValidateHandler> validateHandlers, VoucherMapper voucherMapper, VoucherRepository voucherRepository) {
            super(validateHandlers, voucherMapper, voucherRepository);
            this.validateHandlers = validateHandlers;
            this.voucherMapper = voucherMapper;
            this.voucherRepository = voucherRepository;
        }

        @Override
        public void create(VoucherRequestDto voucherRequestDto) {
            Long discount = voucherRequestDto.discount();
            VoucherType voucherType = voucherRequestDto.voucherType();

            ValidateHandler handler = validateHandlers.get(voucherType);

            if (handler == null) {
                throw new VoucherValidatorNotFoundException();
            }

            handler.validate(discount);
            Voucher voucher = voucherMapper.mapToVoucher(voucherRequestDto);
            voucherRepository.save(voucher);
        }
    }

    @Component("testFileVoucherRepository")
    @Primary
    static class TestFileVoucherRepository extends FileVoucherRepository {

        private final File FILE;
        private final CSVFileManager csvFileManager;
        private final VoucherMapper voucherMapper;
        private final FileProperties fileProperties;

        ReentrantLock lock = new ReentrantLock();

        public TestFileVoucherRepository(CSVFileManager csvFileManager, VoucherMapper voucherMapper, FileProperties fileProperties, ExternalProperties externalProperties) {
            super(csvFileManager, voucherMapper, fileProperties, externalProperties);
            this.csvFileManager = csvFileManager;
            this.voucherMapper = voucherMapper;
            this.fileProperties = fileProperties;
            String filePath = getTestFilePath();
            FILE = new File(filePath);
        }

        private String getTestFilePath() {

            String folderPath = fileProperties.getUserDir();
            String fileName = fileProperties.getNames().get("voucher").getFileName();
            String resourcePath = fileProperties.getResources().getPath();
            return folderPath + resourcePath + fileName;
        }

        @Override
        public void save(Voucher voucher) {
            lock.lock();
            try {
                writeVoucherToFile(voucher);
            } finally {
                lock.unlock();
            }
        }

        private void writeVoucherToFile(Voucher voucher) {

            try {
                BufferedWriter writer = csvFileManager.getBufferedWriter(FILE);

                String serializer = voucherMapper.serialize(voucher);
                writer.write(serializer);
                writer.newLine();
                writer.flush();

            } catch (FileNotFoundException e) {
                throw new CSVFileIOFailureException("Exception Occurred: No matching file found!");
            } catch (IOException e) {
                throw new CSVFileIOFailureException("Exception Occurred: Failed to save file!");
            }
        }

        @Override
        public List<Voucher> findAll() {
            lock.lock();
            try {
                return getVoucherFromFile();
            } finally {
                lock.unlock();
            }
        }

        private List<Voucher> getVoucherFromFile() {

            List<Voucher> voucherList = new ArrayList<>();
            String line;

            try {
                BufferedReader reader = csvFileManager.getBufferedReader(FILE);

                while ((line = reader.readLine()) != null) {
                    String[] token = line.split(AppConstants.CSV_SEPARATOR);
                    CsvVoucherDto voucherDto = voucherMapper.deserialize(token);
                    Voucher voucher = voucherMapper.mapToVoucher(voucherDto);
                    voucherList.add(voucher);
                }
            } catch (FileNotFoundException e) {
                throw new CSVFileIOFailureException("Exception Occurred: No matching file found!");
            } catch (IOException e) {
                throw new CSVFileIOFailureException("Exception Occurred: Failed to read file!");
            }

            return voucherList;
        }
    }

    @Test
    @DisplayName("파일 쓰기에 대한 동시성 제어 성공 테스트")
    public void successConcurrencyControlAboutFileWrite() throws InterruptedException {

        Voucher voucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 10L, VoucherType.PERCENT);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 50L, VoucherType.PERCENT);
        Voucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 60L, VoucherType.PERCENT);
        Voucher voucher4 = new FixedAmountVoucher(UUID.randomUUID(), 1000L, VoucherType.FIXED);
        Voucher voucher5 = new FixedAmountVoucher(UUID.randomUUID(), 1500L, VoucherType.FIXED);

        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        try {
            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    testFileVoucherRepository.save(voucher1);
                    testFileVoucherRepository.save(voucher2);
                    testFileVoucherRepository.save(voucher3);
                    testFileVoucherRepository.save(voucher4);
                    testFileVoucherRepository.save(voucher5);
                });
            }

            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } finally {
            executorService.shutdownNow();
        }

        testFileVoucherRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void testConcurrency() throws InterruptedException {

        int threadCount = 20;
        int iterations = 100;

        CountDownLatch latch = new CountDownLatch(threadCount);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        Runnable task = () -> {
            for (int i = 0; i < iterations; i++) {
                testVoucherService.create(new VoucherRequestDto(VoucherType.FIXED, 50L));
            }
            latch.countDown();
        };

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(task);
        }

        latch.await();

        List<Voucher> vouchers = testFileVoucherRepository.findAll();
        assertThat(iterations * threadCount).isEqualTo(vouchers.size());
        executorService.shutdown();
    }
}

