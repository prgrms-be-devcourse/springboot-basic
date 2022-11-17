package org.prgrms.kdt.dao.entity;

import java.util.Date;

public class BlackList {
    private final String name;
    private final Date birthDate;

    public BlackList(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "BlackList{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
