package com.voucher.vouchermanagement.repository.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileIO implements FileOutput, FileInput {

  private static final Logger logger = LoggerFactory.getLogger(FileIO.class);

  public void writeln(File file, String content) {
    try {
      FileWriter fileWriter = new FileWriter(file, true);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      bufferedWriter.write(content);
      bufferedWriter.newLine();
      bufferedWriter.close();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  @Override
  public List<String> readAllLine(File file) {
    try {
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = "";
      List<String> lines = new ArrayList<>();
      while ((line = bufferedReader.readLine()) != null) {
        lines.add(line);
      }
      bufferedReader.close();

      return lines;
    } catch (IOException e) {
      logger.error(e.getMessage());
    }

    return Collections.emptyList();
  }
}
