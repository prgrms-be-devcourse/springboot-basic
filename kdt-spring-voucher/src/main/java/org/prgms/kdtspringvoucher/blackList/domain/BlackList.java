package org.prgms.kdtspringvoucher.blackList.domain;

import java.util.UUID;

public class BlackList {

    private UUID blackListId;
    private String blackListName;

    public BlackList(UUID blackListId, String blackListName) {
        this.blackListId = blackListId;
        this.blackListName = blackListName;
    }

    public UUID getBlackListId() {
        return blackListId;
    }

    public String getBlackListName() {
        return blackListName;
    }

    @Override
    public String toString() {
        return "blackListId=" + blackListId +
                ", blackListName='" + blackListName + '\'';
    }
}
