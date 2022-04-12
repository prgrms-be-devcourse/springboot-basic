package org.programmers.devcourse.voucher.engine.voucher.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.function.Function;
import org.programmers.devcourse.voucher.configuration.FileDBProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class FileChannel implements AutoCloseable {

  private final BufferedWriter fileWriter;
  private final BufferedReader fileReader;


  public FileChannel(FileDBProperties properties) throws FileNotFoundException {
    String rootPath = System.getProperty("user.dir");
    var dbFile = Path.of(rootPath, properties.getFilename()).toFile();

    fileWriter = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(dbFile, true)));
    fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(dbFile)));

  }

  public String[] readAllLines() {
    return fileReader.lines().toArray(String[]::new);
  }

  public <T> void save(T data, Function<T, String> serializer) throws IOException {
    fileWriter.append(serializer.apply(data));
    fileWriter.newLine();
    fileWriter.flush();
  }

  @Override
  public void close() throws Exception {
    fileReader.close();
    fileWriter.close();
  }
}
