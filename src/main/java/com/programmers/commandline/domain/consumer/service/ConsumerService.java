package com.programmers.commandline.domain.consumer.service;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.repository.ConsumerRepository;
import com.programmers.commandline.global.io.Message;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConsumerService {

    private final ConsumerRepository consumerRepository;

    ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    public String insert(UUID uuid, String name, String email) {
        Consumer consumer = new Consumer(uuid, name, email, LocalDateTime.now());
        return consumerRepository.insert(consumer).getConsumerId().toString();
    }

    public String findAll() {
        StringBuilder consumers = new StringBuilder();

        consumerRepository.findAll().forEach(consumer -> {
            consumers.append(consumer.toString());
        });

        return consumers.toString();
    }

    public String update(UUID uuid, String name, String email) {

        Consumer consumer = consumerRepository.findById(uuid).orElseThrow(() -> {
            throw new NullPointerException(Message.NULL_POINT.getMessage());
        });
        consumer.update(name, email);

        return consumerRepository.update(consumer).getConsumerId().toString();
    }

    public Consumer findById(UUID uuid) {
        return consumerRepository.findById(uuid).orElseThrow(() -> {
            throw new NullPointerException(Message.NULL_POINT.getMessage());
        });
    }

    public Consumer findByName(String name) {
        return consumerRepository.findByName(name).orElseThrow(() -> {
            throw new NullPointerException(Message.NULL_POINT.getMessage());
        });
    }

    public Consumer findByEmail(String email) {
        return consumerRepository.findByEmail(email).orElseThrow(() -> {
            throw new NullPointerException(Message.NULL_POINT.getMessage());
        });
    }

    public void deleteAll() {
        consumerRepository.deleteAll();
    }
}
