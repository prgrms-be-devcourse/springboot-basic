package org.prgrms.memory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.prgrms.exception.FileNotExistException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerBlackListFileMemory {

  private final File file;

  public CustomerBlackListFileMemory(@Value("${file.customer.file-path}") String filePath) {
    this.file = new File(filePath);
  }

  public List<String> findAll(){
    List<String> blacklist = new ArrayList<>();
    if (file.exists()) {
      try(BufferedReader reader = new BufferedReader(new FileReader(file))) {

        String line;

        while ((line = reader.readLine()) != null) {
          blacklist.add(line);
        }
      }catch (IOException e) {
        throw new FileNotExistException(e);
      }
    }
    return blacklist;
  }

}
