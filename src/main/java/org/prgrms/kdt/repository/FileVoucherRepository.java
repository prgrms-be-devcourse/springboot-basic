package org.prgrms.kdt.repository;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class FileVoucherRepository implements VoucherRepository {

  private static final Logger log = LoggerFactory.getLogger(FileVoucherRepository.class);
  private final ResourceLoader resourceLoader;

  @Value("${path.voucher}")
  private String fileName;

  public FileVoucherRepository(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Override
  public Optional<Voucher> findById(UUID voucherId) {
    return Optional.empty();
  }

  @Override
  public Voucher save(Voucher voucher) {
    write(voucher, getResource());
    return voucher;
  }

  @Override
  public Voucher update(Voucher voucher) {
    return null;
  }

  @Override
  public List<Voucher> findAll() {
    return readText(getResource());
  }

  private Resource getResource() {
    return resourceLoader.getResource(fileName);
  }

  private void write(Voucher voucher, Resource resource) {
    try (
        var out = new FileOutputStream(resource.getFile(), true);
        var writer = new ObjectOutputStream(out)) {
      writer.writeObject(voucher);
    } catch (IOException e) {
      log.error("I/O Error: Can't write object", e);
    }
  }

  private List<Voucher> readText(Resource resource) {
    var vouchers = new ArrayList<Voucher>();

    try (var in = new FileInputStream(resource.getFile())) {
      while (true) {
        var reader = new ObjectInputStream(in);
        var e = (Voucher) reader.readObject();
        vouchers.add(e);
      }
    } catch (EOFException e) {
      log.info("End of file");
    } catch (IOException | ClassNotFoundException e) {
      log.error("I/O Error: Can't read object", e);
      return Collections.emptyList();
    }
    return vouchers;
  }
}