package org.prgrms.kdt.utils;

public enum Sequence {

    ID_SEQUENCE(1L);

    Long matchNumber;

    Sequence(Long matchNumber) {
        this.matchNumber = matchNumber;
    }
}
