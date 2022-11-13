package com.prgrms.springbootbasic.user.blacklist;

import static com.prgrms.springbootbasic.common.exception.ExceptionMessage.FATAL_FILE_IO_EXCEPTION_MESSAGE;
import static com.prgrms.springbootbasic.common.exception.ExceptionMessage.FILE_NOT_EXIST_EXCEPTION_MESSAGE;

import com.prgrms.springbootbasic.common.exception.FatalFileIOException;
import com.prgrms.springbootbasic.common.exception.FileNotExistException;
import com.prgrms.springbootbasic.user.domain.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

@Repository
public class BlacklistStorage {

  private static final String DELIMITER = ",";
  private static final int USER_ID_COLUMN_INDEX = 0;
  private static final int NAME_COLUMN_INDEX = 1;

  @Value("${classpath.customer-blacklist}")
  private String CLASSPATH_BLACKLIST;

  private final ResourceLoader resourceLoader;

  private final Logger logger = LoggerFactory.getLogger(BlacklistStorage.class);

  public BlacklistStorage(ResourceLoader resourceLoader){
    this.resourceLoader = resourceLoader;
  }

  public List<User> findAll(){
    try {
      File file = openFile();
      return readAll(file);
    } catch (IOException e) {
      String errorMessage = FATAL_FILE_IO_EXCEPTION_MESSAGE + e.getMessage();
      logger.error(errorMessage);
      throw new FatalFileIOException(errorMessage);
    }
  }

  private File openFile() {
    Resource resource = resourceLoader.getResource(CLASSPATH_BLACKLIST);
    try {
      return resource.getFile();
    } catch (FileNotFoundException e) {
      String errorMessage = FILE_NOT_EXIST_EXCEPTION_MESSAGE + e.getMessage();
      logger.error(errorMessage);
      throw new FileNotExistException(errorMessage);
    } catch (IOException e) {
      String errorMessage = FATAL_FILE_IO_EXCEPTION_MESSAGE + e.getMessage();
      logger.error(errorMessage);
      throw new FatalFileIOException(errorMessage);
    }
  }

  private List<User> readAll(File file) throws IOException {
    List<User> blacklist = new ArrayList<>();
    try(BufferedReader br = new BufferedReader(new FileReader(file))){
      String line;
      skipLine(br);
      while((line = br.readLine()) != null){
        blacklist.add(mapToUser(line));
      }
    }
    return blacklist;
  }

  private User mapToUser(String line) {
    String[] columns = line.split(DELIMITER);

    UUID id = UUID.fromString(columns[USER_ID_COLUMN_INDEX].trim());
    String name = columns[NAME_COLUMN_INDEX].trim();
    return new User(id, name);
  }

  private void skipLine(BufferedReader br) throws IOException {
    br.readLine();
  }
}
