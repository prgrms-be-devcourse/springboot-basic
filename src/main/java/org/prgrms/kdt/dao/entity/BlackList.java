package org.prgrms.kdt.dao.entity;

import java.util.Date;

public class BlackList {
    private String name;
    private Date birthDate;

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
