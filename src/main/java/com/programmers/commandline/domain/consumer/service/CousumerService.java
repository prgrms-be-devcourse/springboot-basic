package com.programmers.commandline.domain.consumer.service;

import com.programmers.commandline.domain.consumer.repository.FileConsumerRepository;
import com.programmers.commandline.global.factory.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CousumerService {

    private final FileConsumerRepository fileConsumerRepository;

    CousumerService(FileConsumerRepository fileConsumerRepository) {
        this.fileConsumerRepository = fileConsumerRepository;
    }

    public String blackCousumerList() {
        LoggerFactory.getLogger().info("CousumerService blackCousumerList 실행");
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
