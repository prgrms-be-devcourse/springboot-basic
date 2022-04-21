package com.voucher.vouchermanagement.utils.io.file;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileInput {
    private static final Logger logger = LoggerFactory.getLogger(FileInput.class);

    public List<String> readAllLine(File file) {
        String buffer = "";
        List<String> lines = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((buffer = bufferedReader.readLine()) != null) {
                lines.add(buffer);
            }
            bufferedReader.close();

            return lines;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }

        return Collections.emptyList();
    }
}
