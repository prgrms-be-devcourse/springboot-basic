package org.prgms.management.wallet.repository;

import org.prgms.management.wallet.entity.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local-file", "test"})
public class WalletFileRepository implements WalletRepository {
    @Value("${filedb.wallet}")
    private String filePath;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<UUID, Wallet> getAll() {
        Map<UUID, Wallet> map = new ConcurrentHashMap<>();

        try (
                BufferedReader bufferedReader = new BufferedReader(
                        new FileReader(filePath))
        ) {
            bufferedReader.lines().forEach(
                    line -> {
                        String[] str = line.split(",");
                        UUID walletId = UUID.fromString(str[0]);
                        UUID customerId = UUID.fromString(str[1]);
                        map.put(walletId, new Wallet(walletId, customerId));
                    }
            );
        } catch (IOException e) {
            logger.error("{} can't read wallet file", e.getMessage());
        }

        return map;
    }

    @Override
    public Optional<Wallet> insert(Wallet wallet) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new FileWriter(filePath, true)
                );
        ) {
            String walletStr = MessageFormat.format("{0},{1}\r\n",
                    wallet.getWalletId(), wallet.getCustomerId());

            bufferedWriter.write(walletStr);
            return Optional.of(wallet);
        } catch (IOException e) {
            logger.error("{} can't insert wallet file", e.getMessage(), e);
        }
        return Optional.empty();
    }
}
