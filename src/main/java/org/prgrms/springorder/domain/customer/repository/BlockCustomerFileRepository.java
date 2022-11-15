package org.prgrms.springorder.domain.customer.repository;

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
import java.util.stream.Collectors;
import javax.annotation.PreDestroy;
import org.prgrms.springorder.config.BlockCustomerProperties;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("file")
public class FileBlockCustomerRepository implements BlockCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileBlockCustomerRepository.class);

    private final Map<UUID, BlockCustomer> storage = new ConcurrentHashMap<>();

    private final File fileStore;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd HH:mm:ss.SSS");

    public FileBlockCustomerRepository(BlockCustomerProperties properties) {
        fileStore = FileUtil.createFile(properties.getPath(), properties.getStoredName() + properties.getStoredExtension());

        readAll();
    }

    @Override
    public Optional<BlockCustomer> findById(UUID blockId) {
        return Optional.ofNullable(storage.get(blockId));
    }

    @Override
    public BlockCustomer insert(BlockCustomer blockCustomer) {
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

    private void writeAll() {
        String allDate = this.storage.values()
            .stream()
            .map(this::serialize)
            .collect(Collectors.joining(System.lineSeparator()));

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileStore, false))) {
            bufferedWriter.write(allDate);
            bufferedWriter.newLine();
        } catch (IOException e) {
            logger.warn("insert file error {}, {}", e.getMessage(), e.getClass().getName(), e);
            throw new RuntimeException("file i/o error", e);
        }
    }

    @PreDestroy
    private void preDestroy() {
        writeAll();
    }

}
