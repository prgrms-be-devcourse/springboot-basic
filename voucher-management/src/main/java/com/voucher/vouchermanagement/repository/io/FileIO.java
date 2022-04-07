package com.voucher.vouchermanagement.repository.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FileIO implements FileOutput, FileInput {

  public void writeln(File file, String content) throws IOException {
    FileWriter fileWriter = new FileWriter(file, true);
    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    bufferedWriter.write(content);
    bufferedWriter.newLine();
    bufferedWriter.close();
  }

  @Override
  public List<String> readAllLine(File file) throws IOException {
    FileReader fileReader= new FileReader(file);
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    String line = "";
    List<String> lines = new ArrayList<>();
    while ((line = bufferedReader.readLine()) != null) {
      lines.add(line);
    }
    bufferedReader.close();

    return lines;
  }
}
