package com.programmers.commandLine.domain.consumer.service;

import com.programmers.commandLine.domain.consumer.repository.FileConsumerRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConsumerService {

    private FileConsumerRepository fileConsumerRepository;

    ConsumerService(FileConsumerRepository fileConsumerRepository) {
        this.fileConsumerRepository = fileConsumerRepository;
    }

    public String blackList() {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> findAll = fileConsumerRepository.findAll();
        findAll.forEach((uuid,nickName) -> {
            stringBuilder.append(
                    "ID: " + uuid + " NickName: " + nickName
            );
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

}
