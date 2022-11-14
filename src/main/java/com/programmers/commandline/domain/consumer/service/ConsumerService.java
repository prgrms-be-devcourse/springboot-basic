package com.programmers.commandline.domain.consumer.service;

import com.programmers.commandline.domain.consumer.repository.FileConsumerRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private final FileConsumerRepository fileConsumerRepository;

    ConsumerService(FileConsumerRepository fileConsumerRepository) {
        this.fileConsumerRepository = fileConsumerRepository;
    }

    public String findAll() {
        StringBuilder consumers = new StringBuilder();

        fileConsumerRepository.findAll().forEach(consumer -> {
            consumers.append(consumer.toString());
        });
        return consumers.toString();
    }
}
