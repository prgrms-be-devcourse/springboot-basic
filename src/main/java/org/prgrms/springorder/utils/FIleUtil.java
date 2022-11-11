package org.prgrms.springorder.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FIleUtil {
	public static List<String> readFile(File file) {
		List<String> list = new ArrayList<>();
		try {
			list = Files.readAllLines(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
