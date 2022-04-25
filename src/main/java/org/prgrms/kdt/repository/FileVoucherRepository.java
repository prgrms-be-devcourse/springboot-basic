package org.prgrms.kdt.repository;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
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
    var vouchers = findAll();
    return vouchers.stream().filter(voucher -> voucher.getVoucherId() == voucherId).findFirst();
  }

  @Override
  public Optional<Voucher> save(Voucher voucher) {
    write(voucher, getResource());
    return Optional.ofNullable(voucher);
  }

  @Override
  public Optional<Voucher> update(Voucher voucher) {
    var vouchers = findAll();
    var voucherIndex = IntStream.range(0, vouchers.size())
        .filter(i -> vouchers.get(i).getVoucherId() == voucher.getVoucherId())
        .findFirst().orElseThrow();
    vouchers.remove(voucherIndex);
    vouchers.add(voucherIndex, voucher);
    deleteAll();
    vouchers.forEach(this::save);
    return Optional.of(voucher);
  }

  @Override
  public List<Voucher> findAll() {
    return readText(getResource());
  }

  @Override
  public void delete(UUID voucherId, UUID customerId) {
    var vouchers = findAll();
    var voucher = vouchers.stream().filter(
            v -> v.getVoucherId() == voucherId && v.getCustomerId() == customerId)
        .findFirst().orElseThrow();
    vouchers.remove(voucher);
    vouchers.forEach(this::save);
  }

  @Override
  public void deleteAll() {
    try (var ignored = new PrintWriter(getResource().getFile())) {
      log.info("delete all vouchers");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Voucher> findByCustomerId(UUID customerId) {
    return null;
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