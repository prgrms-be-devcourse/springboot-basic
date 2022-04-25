package com.voucher.vouchermanagement.utils.io.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileOutput {
    private static final Logger logger = LoggerFactory.getLogger(FileInput.class);

    public void writeln(File file, String line, boolean appendMode) {
        try {
            FileWriter fileWriter = new FileWriter(file, appendMode);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(line);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
    }
}
