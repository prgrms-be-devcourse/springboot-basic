package com.programmers.commandline.domain.consumer.service;

import com.programmers.commandline.domain.consumer.repository.FileConsumerRepository;
import org.springframework.stereotype.Service;

@Service
public class CousumerService {

    private final FileConsumerRepository fileConsumerRepository;

    CousumerService(FileConsumerRepository fileConsumerRepository) {
        this.fileConsumerRepository = fileConsumerRepository;
    }

    public String blackCousumerList() {
        StringBuilder stringBuilder = new StringBuilder();

        fileConsumerRepository.findAll().forEach(cousumer -> {
            stringBuilder.append("ID: ");

            stringBuilder.append(cousumer.getCousumerId());

            stringBuilder.append(" NickName: ");

            stringBuilder.append(cousumer.getNickName());

            stringBuilder.append("\n");
        });

        return stringBuilder.toString();
    }
}
