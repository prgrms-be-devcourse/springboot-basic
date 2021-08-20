package org.prgrms.kdt.blacklist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Person {
    private final UUID personId;
    private String name;
    private String phoneNumber;
    private String address;

    @Override
    public String toString() {
        String s = personId+","+name+","+phoneNumber+","+address;
        return s;
    }
}
