package org.prgrms.kdt.member;

import org.prgrms.kdt.exception.DatabaseException;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.util.Converter;
import org.prgrms.kdt.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemberLoader {
    private final String filePath;

    public MemberLoader(@Value("${filePath.blackList}") String filePath) {
        this.filePath = filePath;
    }

    public Map<UUID, Member> loadFileToMemoryMember() {
        Map<UUID, Member> Members = new ConcurrentHashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";

            while ((line = reader.readLine()) != null) {
                UUID curId = UUID.fromString(Converter.stringToArray(line, ",")[0]);
                Members.put(curId, Converter.stringToMember(line, MemberStatus.BLACK));
            }
            return Members;
        } catch (IOException e) {
            throw new DatabaseException(ErrorMessage.FILE_ACCESS_ERROR, e);
        }
    }

    public void saveMemoryMemberToFile(Map<UUID, Member> memoryStorage) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            for (Map.Entry<UUID, Member> entry : memoryStorage.entrySet()) {
                writer.append(Converter.memberToString(entry.getValue()) + "\n");
            }
        } catch (IOException e) {
            throw new DatabaseException(ErrorMessage.FILE_ACCESS_ERROR, e);
        }
    }
}
