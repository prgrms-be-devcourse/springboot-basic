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
    private BlackListRepository blackListRepository;
    @Autowired
    private BlackListReader blackListReader;
    @Autowired
    private BlackListWriter blackListwriter;

    public BlackListService(BlackListRepository blackListRepository, BlackListReader blackListReader, BlackListWriter blackListwriter) {
        this.blackListRepository = blackListRepository;
        this.blackListReader = blackListReader;
        this.blackListwriter = blackListwriter;
    }

    public BlackList getBlacklist(UUID blackListId){
        return blackListRepository
                .findById(blackListId)
                .orElseThrow(()->new RuntimeException(MessageFormat.format("Can not find a BlackList for {0}", blackListId)));
    }

    public void createBlackList(UUID blackListId){
        blackListRepository.insert(new BlackList(blackListId));
    }

    public Map<UUID,BlackList> getBlackLists(){return blackListRepository.returnAll();}

    public void road(Resource resource) throws IOException {
        if(blackListRepository instanceof CsvBlackListRepository) {
            Map<UUID,BlackList> map = blackListReader.readCsv(resource);
            for (UUID uuid : map.keySet()) {
                blackListRepository.insert(map.get(uuid));
            }
        }
    }
    public void save(Resource resource, Map<UUID,BlackList> map) throws IOException {
        if (blackListRepository instanceof CsvBlackListRepository) {
            blackListwriter.writeCsv(resource,blackListRepository.returnAll());
        }
    }

}

