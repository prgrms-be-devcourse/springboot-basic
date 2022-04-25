package org.programmers.springbootbasic.blacklist.repository;

import org.programmers.springbootbasic.blacklist.model.Blacklist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileBlacklistRepository implements BlacklistRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileBlacklistRepository.class);

    @Value("${path.blacklist}")
    private String file;

    public File createFile(File file) {
        try (
                FileOutputStream ignored = new FileOutputStream(file, true);
        ) {
        } catch (IOException e) {
            logger.error("IOException 에러입니다. {0}", e);
        }
        return file;
    }

    @Override
    public List<Blacklist> findAll() {
        List<Blacklist> blackList = new ArrayList<>();

        String line;
        try (
                FileReader fileReader = new FileReader(createFile(new File(file)));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] blackListCustomerInfo = line.split(" ");
                if (blackListCustomerInfo[0].equals("BlackListCustomer")) {
                    Blacklist blacklistMember =
                            new Blacklist(UUID.fromString(blackListCustomerInfo[2]), blackListCustomerInfo[4]);
                    blackList.add(blacklistMember);
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException error");
        } catch (IOException e) {
            logger.error("find IO exception error");
        }
        return blackList;
    }
}


