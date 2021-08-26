package org.prgrms.kdtspringhw.blacklist;

import org.prgrms.kdtspringhw.blacklist.repository.BlackListRepository;
import org.prgrms.kdtspringhw.blacklist.repository.CsvBlackListRepository;
import org.prgrms.kdtspringhw.utils.BlackListReader;
import org.prgrms.kdtspringhw.utils.BlackListWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Service
public class BlackListService {
    @Autowired
    private final BlackListRepository blackListRepository;

    public BlackListService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public BlackList getBlacklist(UUID blackListId){
        return blackListRepository
                .findById(blackListId)
                .orElseThrow(()->new RuntimeException(MessageFormat.format("Can not find a BlackList for {0}", blackListId)));
    }

    public Map<UUID,BlackList> getBlackLists(){return blackListRepository.returnAll();}

}

