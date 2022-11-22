package com.programmers.commandline.domain.consumer.service;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.repository.ConsumerRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class ConsumerService {

    private final ConsumerRepository consumerRepository;

    ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    public Consumer insert(UUID uuid, String name, String email) {
        Consumer consumer = new Consumer(uuid, name, email, LocalDateTime.now());
        return consumerRepository.insert(consumer);
    }

    public String findAll() {
        StringBuilder consumers = new StringBuilder();

        consumerRepository.findAll().forEach(consumer -> {
            consumers.append(consumer.toString());
        });

        return consumers.toString();
    }
}
