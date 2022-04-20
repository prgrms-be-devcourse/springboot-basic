package org.prgms.springbootbasic.voucher.repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.prgms.springbootbasic.voucher.service.VoucherType;
import org.prgms.springbootbasic.voucher.vo.Voucher;
import org.prgms.springbootbasic.voucher.vo.VoucherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;

/**
 * FileVoucherRepository
 * 동작 방법. Bean을 Initialize 할 때 파일에 저장되어 있던 Voucher들을 Parser를 통해 저장
 * Bean이 destroy될 때 파일에 다시 Voucher 정보 저장
 */
@Repository
public class FileVoucherRepository implements VoucherRepository{
	private static final Logger log = LoggerFactory.getLogger(FileVoucherRepository.class);

	private final Map<UUID, Voucher> memory = new ConcurrentHashMap<>();

	/**
	 * Voucher를 memory에 저장하는 메서드
	 *
	 * @param voucher
	 * @return 저장된 Voucher
	 */
	@Override
	public UUID save(Voucher voucher) {
		Preconditions.checkArgument(voucher != null, "voucher은 null이면 안됩니다!");

		memory.put(voucher.getVoucherId(), voucher);
		log.info(MessageFormat.format("Voucher가 저장되었습니다. voucher : {0}", voucher.toString()));
		return voucher.getVoucherId();
	}

	/**
	 * 저장된 Voucher들을 Voucher의 종류에 따라 조회하는 메서드
	 *
	 * @return Map<String, List < Voucher>>
	 */
	@Override
	public Map<VoucherType, List<Voucher>> getVoucherListByType() {
		return memory.values().stream()
			.collect(Collectors.groupingBy(Voucher::getVoucherType));
	}

	@Override
	public int getTotalVoucherCount() {
		return memory.size();
	}

	@Override
	public Voucher findById(UUID voucherId) {
		return memory.entrySet().stream()
			.filter(e -> e.getKey().equals(voucherId))
			.map(e -> e.getValue())
			.findAny()
			.orElseThrow(() -> {
				throw new IllegalArgumentException(
					MessageFormat.format("존재하지 않는 ID를 입력하셨습니다! id : {0}", voucherId));
			});
	}


	@PostConstruct
	private void initializeFileVoucherRepository(){
		log.info("FileVoucherRepository initilaize.");
	}

	@PreDestroy
	private void destroyFileVoucherRepository(){
		// log.info("FileVoucherRepository destroyed.");
		// String path = "";
		// try {
		// 	new FileUrlResource(path);
		// } catch (MalformedURLException e) {
		// 	e.printStackTrace();
		// }
		// final BufferedWriter bufferedWriter = Files.newBufferedWriter(path);
		// memory.values().stream()
		// 	.map(v -> VoucherInfo.of(v.getVoucherId(), v.getVoucherType(), v.getValue()))
		// 	.forEach(info -> {
		// 		try {
		// 			bufferedWriter.write(info + "\n");
		// 		} catch (IOException e) {
		// 			e.printStackTrace();
		// 		}
		// 	});
		//
		// bufferedWriter.flush();
		// bufferedWriter.close();


	}

}
