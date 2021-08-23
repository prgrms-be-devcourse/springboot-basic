package org.prgrms.kdtspringhw.blacklist;

import java.io.Serializable;
import java.util.UUID;

public class BlackList  {
    private final UUID BlackListId;

    public BlackList(UUID blackListId) {
        this.BlackListId = blackListId;
    }


    public UUID getBlackListId() {
        return BlackListId;
    }

    @Override
    public String toString() {
        return "Blacklist{" +
                "BlackListId=" + BlackListId +
                '}';
    }

}
