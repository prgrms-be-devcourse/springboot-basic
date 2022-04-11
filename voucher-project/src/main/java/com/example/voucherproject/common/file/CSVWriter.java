package com.example.voucherproject.common.file;

import com.example.voucherproject.common.enums.UserType;
import com.example.voucherproject.user.domain.User;
import com.example.voucherproject.user.domain.UserFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Component
public class CSVWriter {

    private String saveDirectory = "./src/main/resources/";

    public void userToCSV(User user, String fileName) {
        try {
            var file = getFile(saveDirectory + fileName);
            writeUser(file, user);
        } catch (IOException e) {
            log.error("error");
        }
    }

    private void writeUser(File file, User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

            var id = user.getId();
            var type = user.getType();
            var name = user.getName();

            writer.write(id.toString() + "," + type.toString() + "," + name);
            writer.newLine();
        }
    }

    private File getFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
