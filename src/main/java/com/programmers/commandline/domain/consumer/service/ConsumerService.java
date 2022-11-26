package com.programmers.commandline.domain.consumer.service;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.repository.ConsumerRepository;
import com.programmers.commandline.global.io.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ConsumerService {

    private final ConsumerRepository consumerRepository;
    Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    public String insert(UUID uuid, String name, String email) {
        Consumer consumer = new Consumer(uuid, name, email, LocalDateTime.now());
        return consumerRepository.insert(consumer).getId();
    }

    public List<Consumer> findAll() {
        return consumerRepository.findAll();
    }

    public String update(String consumerId, String name, String email) {

        Consumer consumer = consumerRepository.findById(consumerId).orElseThrow(() -> {
            logger.info(Message.OPTIONAL_NULL.getMessage());
            throw new IllegalArgumentException(Message.NULL_POINT.getMessage());
        });
        consumer.update(name, email);

        return consumerRepository.update(consumer).getId();
    }

    public Consumer findById(String consumerId) {
        return consumerRepository.findById(consumerId).orElseThrow(() -> {
            logger.info(Message.OPTIONAL_NULL.getMessage());
            throw new IllegalArgumentException(Message.NULL_POINT.getMessage());
        });
    }

    public Consumer findByName(String name) {
        return consumerRepository.findByName(name).orElseThrow(() -> {
            logger.info(Message.OPTIONAL_NULL.getMessage());
            throw new IllegalArgumentException(Message.NULL_POINT.getMessage());
        });
    }

    public Consumer findByEmail(String email) {
        return consumerRepository.findByEmail(email).orElseThrow(() -> {
            logger.info(Message.OPTIONAL_NULL.getMessage());
            throw new IllegalArgumentException(Message.NULL_POINT.getMessage());
        });
    }

    public void deleteAll() {
        consumerRepository.deleteAll();
    }

    public String insert(String name, String email) {
        Consumer consumer = new Consumer(UUID.randomUUID(), name, email, LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));
        return consumerRepository.insert(consumer).getId();
    }
}
