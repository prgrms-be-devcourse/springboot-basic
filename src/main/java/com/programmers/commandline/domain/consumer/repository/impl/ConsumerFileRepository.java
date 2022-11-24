package com.programmers.commandline.domain.consumer.repository.impl;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.repository.ConsumerRepository;
import com.programmers.commandline.global.io.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("file")
public class ConsumerFileRepository implements ConsumerRepository {
    private final String filePath;

    public ConsumerFileRepository(@Value("${file.consumerBlacklistPath}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Consumer insert(Consumer customer) {
        TomlWriter tomlWriter = new TomlWriter();
        File consumerFile = new File(filePath + customer.getId());
        try {
            tomlWriter.write(customer, consumerFile);
        } catch (IOException e) {
            throw new IllegalArgumentException(Message.CONSUMER_FILE_WRITE_ERROR.getMessage());
        }
        return customer;
    }

    @Override
    public Consumer update(Consumer customer) {
        TomlWriter tomlWriter = new TomlWriter();
        try {
            File consumerFile = new File(filePath + customer.getId());
            if (!consumerFile.exists()) {
                throw new NullPointerException(Message.NULL_POINT_FILE.getMessage());
            }
            tomlWriter.write(customer, consumerFile);
            return new Toml().to(Consumer.class);
        } catch (IOException e) {
            throw new RuntimeException(Message.CONSUMER_FILE_WRITE_ERROR.getMessage());
        }
    }

    @Override
    public int count() {
        File consumerFile = new File(filePath);
        return consumerFile.list().length;
    }

    @Override
    public List<Consumer> findAll() {
        Toml toml = new Toml();
        List<Consumer> consumers = new ArrayList<>();
        File[] consumerFiles = new File(filePath).listFiles();

        for (File consumerFile : consumerFiles) {
            Toml consumerToml = toml.read(consumerFile);

            UUID id = UUID.fromString(consumerToml.getString("id"));
            String name = consumerToml.getString("name");
            String email = consumerToml.getString("email");
            LocalDateTime createdAt = LocalDateTime.parse(consumerToml.getString("createdAt"));
            LocalDateTime lastLoginAt = consumerToml.getString("lastLoginAt") != null ?
                    LocalDateTime.parse(consumerToml.getString("lastLoginAt")) : null;

            Consumer consumer = new Consumer(id, name, email, createdAt, lastLoginAt);
            consumers.add(consumer);
        }
        return consumers;
    }

    @Override
    public Optional<Consumer> findById(String consumerId) {
        Toml toml = new Toml();
        File consumerFile = new File(filePath + consumerId);
        Toml consumerToml = toml.read(consumerFile);

        UUID id = UUID.fromString(consumerToml.getString("id"));
        String name = consumerToml.getString("name");
        String email = consumerToml.getString("email");
        LocalDateTime createdAt = LocalDateTime.parse(consumerToml.getString("createdAt"));
        LocalDateTime lastLoginAt = consumerToml.getString("lastLoginAt") != null ? LocalDateTime.parse(consumerToml.getString("lastLoginAt")) : null;

        Consumer consumer = new Consumer(id, name, email, createdAt, lastLoginAt);
        return Optional.ofNullable(consumer);
    }

    @Override
    public Optional<Consumer> findByName(String name) {
        Toml toml = new Toml();
        File[] consumerFiles = new File(filePath).listFiles();

        for (File consumer : consumerFiles) {
            Toml consumerToml = toml.read(consumer);
            if (consumerToml.getString("name").equals(name)) {
                UUID id = UUID.fromString(consumerToml.getString("id"));
                String email = consumerToml.getString("email");
                LocalDateTime createdAt = LocalDateTime.parse(consumerToml.getString("createdAt"));
                LocalDateTime lastLoginAt = consumerToml.getString("lastLoginAt") != null ? LocalDateTime.parse(consumerToml.getString("lastLoginAt")) : null;

                return Optional.ofNullable(new Consumer(id, name, email, createdAt, lastLoginAt));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Consumer> findByEmail(String email) {
        Toml toml = new Toml();
        File[] consumerFiles = new File(filePath).listFiles();

        for (File consumer : consumerFiles) {
            Toml consumerToml = toml.read(consumer);
            if (consumerToml.getString("email").equals(email)) {
                UUID id = UUID.fromString(consumerToml.getString("id"));
                String name = consumerToml.getString("name");
                LocalDateTime createdAt = LocalDateTime.parse(consumerToml.getString("createdAt"));
                LocalDateTime lastLoginAt = consumerToml.getString("lastLoginAt") != null ? LocalDateTime.parse(consumerToml.getString("lastLoginAt")) : null;

                return Optional.ofNullable(new Consumer(id, name, email, createdAt, lastLoginAt));
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteAll() {
        File[] consumerFiles = new File(filePath).listFiles();

        for (File consumerFile : consumerFiles) {
            consumerFile.delete();
        }
    }
}
