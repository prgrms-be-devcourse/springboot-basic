package org.prgrms.kdt.domain.member.repository;

import org.prgrms.kdt.domain.member.model.Member;
import org.prgrms.kdt.util.CsvUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileMemberRepository implements MemberRepository{
    @Value("${csv.member.path}")
    private String csvPath;
    @Value("${csv.member.file-name}")
    private String fileName;
    private static final int UUID_INDEX = 0;
    private static final int NAME_INDEX = 1;

    @Override
    public List<Member> findAll() {
        List<List<String>> csvData = CsvUtils.readCsv(csvPath, fileName);
        return parseCsvToList(csvData);
    }

    private List<Member> parseCsvToList(List<List<String>> csvData) {
        List<Member> members = new ArrayList<>();
        for (List<String> row : csvData) {
            UUID customerId = UUID.fromString(row.get(UUID_INDEX));
            String name = row.get(NAME_INDEX);
            members.add(new Member(customerId, name));
        }
        return members;
    }
}
