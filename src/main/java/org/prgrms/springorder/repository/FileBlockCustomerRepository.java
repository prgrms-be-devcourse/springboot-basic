package org.prgrms.springorder.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.springorder.domain.BlockCustomer;
import org.prgrms.springorder.exception.DuplicateIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class FileBlockCustomerRepository implements BlockCustomerRepository {

    // 프로그램 시작 종료시 덤프 생각해보자.
    private static final Logger logger = LoggerFactory.getLogger(FileBlockCustomerRepository.class);

    private final Map<UUID, BlockCustomer> storage = new ConcurrentHashMap<>();

    private final File fileStore;

    public FileBlockCustomerRepository(
        @Value("${file.path}") String path,
        @Value("${file.stored-name}") String fileName,
        @Value("${file.stored-extension}") String fileExtension) throws IOException {

        String filePath = path + fileName + fileExtension;

        fileStore = new File(filePath);
        createFile();
        readAll();
    }

    private void createFile() throws IOException { // 여기 있기 이상한애
        if (!fileStore.getParentFile().exists()) {
            fileStore.getParentFile().mkdirs();
        }

        if (!fileStore.exists()) {
            fileStore.createNewFile();
        }
    }

    @Override
    public Optional<BlockCustomer> findById(UUID blockId) {
        return Optional.ofNullable(storage.get(blockId));
    }

    @Override
    public BlockCustomer insert(BlockCustomer blockCustomer) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        String insertData = String.format("%s, %s, %s", blockCustomer.getBlockId(),
            blockCustomer.getCustomerId(), blockCustomer.getRegisteredAt().format(formatter));

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileStore, true))) {
            bufferedWriter.write(insertData);
            bufferedWriter.newLine();
        } catch (IOException e) {
            logger.warn("insert file error {}, {}", e.getMessage(), e.getClass().getName(), e);
            throw new RuntimeException("file i/o error", e);
        }

        storage.putIfAbsent(blockCustomer.getBlockId(), blockCustomer);
        return blockCustomer;
    }

    @Override
    public List<BlockCustomer> findAll() {
        readAll();
        return storage.values().stream()
            .toList();
    }

    @Override
    public void deleteAll() {
        deleteFile();

        try {
            createFile();
        } catch (IOException e) {
            logger.error("createNewFile Error. {} ", e.getMessage(), e);
        }

        storage.clear();
    }

    private void deleteFile() {
        if (fileStore.exists()) {
            fileStore.delete();
        }
    }

    private void readAll() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileStore))) {

            String line = "";
            while (((line = bufferedReader.readLine()) != null)) {
                String[] split = line.split(",");

                UUID blockId = UUID.fromString(split[0].trim());

                UUID customerId = UUID.fromString(split[1].trim());
                LocalDateTime registeredAt = LocalDateTime.parse(split[2].trim(), formatter);

                BlockCustomer blockCustomer = new BlockCustomer(blockId, customerId, registeredAt);

                storage.put(blockId, blockCustomer);
            }

        } catch (IOException e) {
            logger.warn("file read Error. {}r", e.getMessage());
            throw new RuntimeException("file i/o error", e);
        }
    }

}
