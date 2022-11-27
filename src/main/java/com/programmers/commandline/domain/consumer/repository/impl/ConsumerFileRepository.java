package com.programmers.commandline.domain.consumer.repository.impl;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.programmers.commandline.domain.consumer.dto.ConsumerFileInsertResponseDto;
import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.repository.ConsumerRepository;
import com.programmers.commandline.global.exception.FileReadNotFoundException;
import com.programmers.commandline.global.exception.FileWriteException;
import com.programmers.commandline.global.io.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("file")
public class ConsumerFileRepository implements ConsumerRepository {
    private final String filePath;

    public ConsumerFileRepository(@Value("${file.consumerPath}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Consumer insert(Consumer consumer) {
        TomlWriter tomlWriter = new TomlWriter();
        File consumerFile = new File(filePath + consumer.getId());
        ConsumerFileInsertResponseDto consumerFileInsertResponseDto = new ConsumerFileInsertResponseDto(consumer);
        try {
            tomlWriter.write(consumerFileInsertResponseDto, consumerFile);
        } catch (IOException e) {
            throw new FileWriteException(Message.CONSUMER_FILE_WRITE_ERROR.getMessage(), e);
        }

        return consumer;
    }

    @Override
    public Consumer update(Consumer updateCustomer) {
        TomlWriter tomlWriter = new TomlWriter();
        try {
            File consumerFile = new File(filePath + updateCustomer.getId());

            if (!consumerFile.exists()) {
                throw new FileReadNotFoundException(Message.NULL_POINT_FILE.getMessage());
            }

            Consumer consumer = convertTomlToConsumer(consumerFile);
            consumer.update(updateCustomer.getName(), updateCustomer.getEmail());
            ConsumerFileInsertResponseDto consumerFileInsertResponseDto = new ConsumerFileInsertResponseDto(consumer);

            tomlWriter.write(consumerFileInsertResponseDto, consumerFile);

            return consumer;
        } catch (IOException e) {
            throw new FileWriteException(Message.CONSUMER_FILE_WRITE_ERROR.getMessage(), e);
        }
    }

    @Override
    public int count() {
        File consumerFile = new File(filePath);
        return consumerFile.list().length;
    }

    @Override
    public List<Consumer> findAll() {
        List<Consumer> consumers = new ArrayList<>();
        List<File> consumerFiles = Arrays.stream(new File(filePath).listFiles()).toList();

        consumerFiles.forEach(consumerFile -> {
            Consumer consumer = convertTomlToConsumer(consumerFile);
            consumers.add(consumer);
        });

        return consumers;
    }

    @Override
    public Optional<Consumer> findById(String id) {
        File consumerFile = new File(filePath + id);
        Consumer consumer = convertTomlToConsumer(consumerFile);
        return Optional.ofNullable(consumer);
    }

    @Override
    public Optional<Consumer> findByName(String name) {
        List<File> consumerFiles = Arrays.stream(new File(filePath).listFiles()).toList();

        for (File consumerFile : consumerFiles) {
            Consumer consumer = convertTomlToConsumer(consumerFile);
            if (consumer.getName().equals(name)) {
                return Optional.ofNullable(consumer);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Consumer> findByEmail(String email) {
        List<File> consumerFiles = Arrays.stream(new File(filePath).listFiles()).toList();

        for (File consumerFile : consumerFiles) {
            Consumer consumer = convertTomlToConsumer(consumerFile);
            if (consumer.getEmail().equals(email)) {
                return Optional.ofNullable(consumer);
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteAll() {
        List<File> consumerFiles = Arrays.stream(new File(filePath).listFiles()).toList();
        consumerFiles.forEach(consumerFile -> consumerFile.delete());
    }

    @Override
    public void deleteById(String id) {
        File consumerFile = new File(filePath + id);
        consumerFile.delete();
    }

    private Consumer convertTomlToConsumer(File file) {
        Toml toml = new Toml();
        Toml consumerToml = toml.read(file);

        UUID id = UUID.fromString(consumerToml.getString("id"));
        String name = consumerToml.getString("name");
        String email = consumerToml.getString("email");
        LocalDateTime createdAt = LocalDateTime.parse(consumerToml.getString("createdAt"));
        LocalDateTime lastLoginAt = LocalDateTime.parse(consumerToml.getString("lastLoginAt"));

        return new Consumer(id, name, email, createdAt, lastLoginAt);
    }
}
