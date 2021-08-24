package org.prgrms.kdt.member.domain;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Member {
    private final static String SPLIT_REGEX_CODE = ",";
    private final static int SPLIT_SIZE = 3;
    private final static int EMAIL_INDEX = 0;
    private final static int NAME_INDEX = 1;
    private final static int PHONE_NUMBER_INDEX = 2;

    private final UUID uuid;
    private final Email email;
    private final Name name;
    private final PhoneNumber phoneNumber;

    public Member(UUID uuid, Email email, Name name, PhoneNumber phoneNumber) {
        this.uuid = uuid;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static Member of(String line) {
        List<String> splitLine = splitResources(line);
        Email email = new Email(splitLine.get(EMAIL_INDEX));
        Name name = new Name(splitLine.get(NAME_INDEX));
        PhoneNumber phoneNumber = new PhoneNumber(splitLine.get(PHONE_NUMBER_INDEX));
        return new Member(UUID.randomUUID(), email, name, phoneNumber);
    }

    private static List<String> splitResources(String line) {
        List<String> splitLine = Arrays.asList(line.split(SPLIT_REGEX_CODE));
        if (splitLine.size() != SPLIT_SIZE) {
            throw new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE);
        }
        return splitLine;
    }

    @Override
    public String toString() {
        return "Member{" +
                "email=" + email.toString() +
                ", name=" + name.toString() +
                ", phoneNumber=" + phoneNumber.toString() +
                '}';
    }
}
