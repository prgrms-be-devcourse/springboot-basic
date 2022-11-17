package com.programmers.commandline.domain.consumer.repository;

import com.moandjiezana.toml.Toml;
import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.global.aop.LogAspect;
import com.programmers.commandline.global.io.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("prod")
public class FileConsumerRepository {

    private final List<Consumer> memory = new ArrayList<>();
    private final String filePath;
    private final File file;

    public FileConsumerRepository(@Value("${file.consumerBlacklistPath}") String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
    }

    public List<Consumer> findAll() {
        File[] files = file.listFiles();
        for (File file : files) {
            Toml toml = new Toml().read(file);
            Long nickname = toml.getLong("nickname");
            String name = toml.getString("name");
            Consumer consumer = new Consumer(nickname, name);
            memory.add(consumer);
        }
        return memory;
    }

}
