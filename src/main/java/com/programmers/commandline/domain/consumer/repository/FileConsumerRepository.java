package com.programmers.commandline.domain.consumer.repository;

import com.programmers.commandline.domain.consumer.entity.Cousumer;
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

@Repository
@Profile("prod")
public class FileConsumerRepository {

    private final List<Cousumer> memory = new ArrayList<>();
    private final String filePath;
    private final File file;

    public FileConsumerRepository(@Value("${file.consumerBlacklistPath}") String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
    }

    public List<Cousumer> findAll() {
        LogAspect.getLogger().info("FileConsumerRepository findAll 실행");
        try {
            FileReader fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] findLine = line.split(",");
                Long cousumerId = Long.parseLong(findLine[0]);
                String nickName = findLine[1];
                Cousumer cousumer = new Cousumer(cousumerId, nickName);
                memory.add(cousumer);
            }
            return memory;
        } catch (IOException e) {
            LogAspect.getLogger().error("FileConsumerRepository findAll 에러 발생");
            throw new IllegalArgumentException(Message.COUSUMER_FILE_READ_ERROR.getMessage());
        }
    }

}
