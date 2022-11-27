package com.programmers.commandline.domain.consumer.service;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.repository.ConsumerRepository;
import com.programmers.commandline.global.io.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        Consumer consumer = new Consumer(uuid, name, email);
        return consumerRepository.insert(consumer).getId();
    }

    public List<Consumer> findAll() {
        List<Consumer> consumers = consumerRepository.findAll();
        consumers.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        return consumers;
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
        Consumer consumer = new Consumer(UUID.randomUUID(), name, email);
        return consumerRepository.insert(consumer).getId();
    }

    public void deleteById(String id) {
        consumerRepository.deleteById(id);
    }
}
