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
import org.prgrms.springorder.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class FileBlockCustomerRepository implements BlockCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileBlockCustomerRepository.class);

    private final Map<UUID, BlockCustomer> storage = new ConcurrentHashMap<>();

    private final File fileStore;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public FileBlockCustomerRepository(
        @Value("${file.block-customer.path}") String path,
        @Value("${file.block-customer.stored-name}") String fileName,
        @Value("${file.block-customer.stored-extension}") String fileExtension){

        fileStore = FileUtil.createFile(path, fileName + fileExtension);

        readAll();
    }

    @Override
    public Optional<BlockCustomer> findById(UUID blockId) {
        return Optional.ofNullable(storage.get(blockId));
    }

    @Override
    public BlockCustomer insert(BlockCustomer blockCustomer) {
        String insertData = serialize(blockCustomer);

        write(insertData);

        storage.putIfAbsent(blockCustomer.getBlockId(), blockCustomer);
        return blockCustomer;
    }

    @Override
    public List<BlockCustomer> findAll() {
        return storage.values().stream()
            .toList();
    }

    @Override
    public void deleteAll() {
        FileUtil.deleteFile(fileStore);
        storage.clear();
    }

    private void readAll() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileStore))) {

            String line = "";
            while (((line = bufferedReader.readLine()) != null)) {

                BlockCustomer blockCustomer = deserialize(line);
                storage.put(blockCustomer.getBlockId(), blockCustomer);
            }

        } catch (IOException e) {
            logger.warn("file read Error. {}", e.getMessage());
            throw new RuntimeException("file i/o error", e);
        }
    }

    private BlockCustomer deserialize(String data) {
        String[] split = data.split(",");

        UUID blockId = UUID.fromString(split[0].trim());

        UUID customerId = UUID.fromString(split[1].trim());
        LocalDateTime registeredAt = LocalDateTime.parse(split[2].trim(), formatter);

        return new BlockCustomer(blockId, customerId, registeredAt);
    }

    private String serialize(BlockCustomer blockCustomer) {
        return String.format("%s, %s, %s", blockCustomer.getBlockId(),
            blockCustomer.getCustomerId(), blockCustomer.getRegisteredAt().format(formatter));
    }

    private void write(String data) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileStore, true))) {
            bufferedWriter.write(data);
            bufferedWriter.newLine();
        } catch (IOException e) {
            logger.warn("insert file error {}, {}", e.getMessage(), e.getClass().getName(), e);
            throw new RuntimeException("file i/o error", e);
        }
    }

}
